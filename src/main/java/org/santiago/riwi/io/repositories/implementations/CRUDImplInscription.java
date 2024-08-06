package org.santiago.riwi.io.repositories.implementations;

import org.santiago.riwi.io.database.dbConnection;
import org.santiago.riwi.io.models.Course;
import org.santiago.riwi.io.models.Inscription;
import org.santiago.riwi.io.models.Student;
import org.santiago.riwi.io.repositories.CRUD.CRUDRepository;
import org.santiago.riwi.io.repositories.managers.CRUDFurtherManager;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDImplInscription implements CRUDFurtherManager<Inscription> {
        //Atributos de CRUDImplInscription
    private static CRUDRepository<Student> studentRepo;
    private static CRUDRepository<Course> courseRepo;
    private static boolean searchingAnding = true;

        //Constructores de CRUDImplInscription
    public CRUDImplInscription() {
        studentRepo = new CRUDImplStudent();
        courseRepo = new CRUDImplCourse();
    }

    //Asignadores de atributos de CRUDImplInscription (setters)
    //Lectores de atributos de CRUDImplInscription (getters)
        //Métodos de CRUDImplInscription
    @Override
    public List<Inscription> toList() throws SQLException {
        List<Inscription> inscriptions = new ArrayList<>();
            //Obtener la conexión
        try(Connection conn = dbConnection.getConnection()){    //Para autocerrar los recursos
            Statement st = conn.createStatement();
            try(ResultSet RS = st.executeQuery("SELECT * FROM inscriptions")){  //Para autocerrar los recursos
                searchingAnding = true;
                while(RS.next()){
                    Inscription inscription = createInscription(RS);
                    inscriptions.add(inscription);
                }
            }
        }
        return inscriptions;
    }

    @Override
    public List<Inscription> toRetrieveList(String field, String search) throws SQLException {    //Una inscripción puede buscarse por course_id o por student_id
        List<Inscription> inscriptions = new ArrayList<>();
        Inscription ins = null;
            //Obtener conexión
        try(Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM inscriptions where " + field +" = ?")){
            switch (field){
                case "course_id" -> ps.setInt(1, Integer.parseInt(search));
                case "student_id" -> ps.setInt(1, Integer.parseInt(search));
            }
            ResultSet RS = ps.executeQuery();
            searchingAnding = true;
            while(RS.next()){
                ins = createInscription(RS);
                inscriptions.add(ins);
            }
        }
        return inscriptions;
    }

    @Override
    public void toSave(Inscription inscription) throws SQLException {
        String sql; //Para definir luego si se guarda, o se modifica un estudiante
        if(inscription.getId() > 0){    //Para modificar
            sql = "UPDATE inscriptions SET course_id = ?, student_id  = ? WHERE id = ? ";
        } else {    //Para ingresar un nuevo estudiante
            sql = "INSERT INTO courses (course_id, student_id) VALUES (?,?)";
        }
        try(Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, inscription.getCourse().getId());
            ps.setInt(2, inscription.getStudent().getId());
            if(inscription.getId() > 0){
                ps.setInt(3, inscription.getId());
            }
            searchingAnding = false;
            toSignUp(inscription.getStudent().getId(), 1); //Para disminuir un cupo al estudiante que de esté matriculando
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                inscription.setId(rs.getInt(1));
                JOptionPane.showConfirmDialog(null, "El estudiante con id " + inscription.getStudent().getId() + " se acaba de inscribir en " + inscription.getCourse().getName());
            } else {
                JOptionPane.showConfirmDialog(null, "La información de la inscripción con id " + inscription.getId() + " acaba de modificarse exitosamente");
            }
        }
    }

    @Override
    public void toDeleteRec(int course_id, int student_id) throws SQLException {  //Hay que dar de baja a un estudiante de un curso
        try(Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM inscriptions WHERE course_id = ? AND student_id = ?")){
            ps.setInt(1, course_id);
            ps.setInt(1, student_id);
            int sizeBefore = this.toList().size();
            ps.executeUpdate();
            int sizeAfter = this.toList().size();
            Course course = null;
            Student student = null;
            if(sizeBefore > sizeAfter){
                course = courseRepo.toRetrieve("id", String.valueOf(course_id));
                student = studentRepo.toRetrieve("id", String.valueOf(student_id));

                searchingAnding = false;
                toSignUp(student.getId(), -1);  //Es decir, libera un cupo
                JOptionPane.showConfirmDialog(null, "El estudiante " + student.getName() + " " + student.getLastName() + " acaba de cancelar el curso " + course.getName());
            } else {
                JOptionPane.showConfirmDialog(null, "El estudiante " + student.getName() + " " + student.getLastName() + " no estaba inscrito en el curso " + course.getName());
            }
        }
    }

    private static Inscription createInscription(ResultSet rs) throws SQLException {
        Inscription i = new Inscription();
        i.setId(rs.getInt("id"));

        Course c = courseRepo.toRetrieve("id", Integer.valueOf(rs.getInt("course_id")).toString());
        Student s = studentRepo.toRetrieve("id", Integer.valueOf(rs.getInt("student_id")).toString());

            i.setStudent(s);
            i.setCourse(c);
        return i;
    }

    private static void toSignUp(int student_id, int movement) throws SQLException {
        if(!searchingAnding){
            Student s = studentRepo.toRetrieve("id", String.valueOf(student_id));
            s.calculateInsAvailable(movement);
            searchingAnding = true;
        }
    }
}
