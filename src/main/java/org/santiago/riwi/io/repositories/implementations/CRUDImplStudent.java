package org.santiago.riwi.io.repositories.implementations;

import org.santiago.riwi.io.database.dbConnection;
import org.santiago.riwi.io.models.Status;
import org.santiago.riwi.io.models.Student;
import org.santiago.riwi.io.repositories.CRUD.CRUDRepository;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDImplStudent implements CRUDRepository<Student> {
        //Atributos de CRUDImplStudent
    public CRUDImplStudent() {
    }

    //Constructores de CRUDImplStudent
    //Asignadores de atributos de CRUDImplStudent (setters)
    //Lectores de atributos de CRUDImplStudent (getters)
        //Métodos de CRUDImplStudent
    @Override
    public List<Student> toList() throws SQLException {
        List<Student> students = new ArrayList<>();
            //Obtener la conexión
        try(Connection conn = dbConnection.getConnection()){    //Para autocerrar los recursos
            Statement st = conn.createStatement();
            try(ResultSet RS = st.executeQuery("SELECT * FROM students")){  //Para autocerrar los recursos
                while(RS.next()){
                    Student student = createStudent(RS);
                    students.add(student);
                }
            }
        }
        return students;
    }

    @Override
    public Student toRetrieve(String field, String search) throws SQLException{ //Un estudiante se lo puede buscar tanto por id, como por email
        Student student = null;
            //Obtener conexión
        try(Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM students where " + field +" = ?")){
            switch (field){
                case "id" -> ps.setInt(1, Integer.parseInt(search));
                case "email" -> ps.setString(1, search);
            }
            ResultSet RS = ps.executeQuery();
            if(RS.next()){
                student = createStudent(RS);
            }
        }
        return student;
    }

    @Override
    public void toSave(Student student) throws SQLException{
        String sql; //Para definir luego si se guarda, o se modifica un estudiante
        if(student.getId() > 0){    //Para modificar
            sql = "UPDATE students SET name = ?, last_name = ?, email = ?, status = ? WHERE id = ? ";
        } else {    //Para ingresar un nuevo estudiante
            sql = "INSERT INTO students (name, last_name, email, status) VALUES (?,?,?,?)";
        }
        try(Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, student.getName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getStatus().toString());
            if(student.getId() > 0){
               ps.setInt(5, student.getId());
            }
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                student.setId(rs.getInt(1));
                JOptionPane.showConfirmDialog(null, "El estudiante con id " + student.getId() + " acaba de inscribirse en nuestra escuela");
            } else {
                JOptionPane.showConfirmDialog(null, "La información del estudiante con id " + student.getId() + " acaba de modificarse exitosamente");
            }
        }
    }

    @Override
    public void toDelete(int id) throws SQLException{
        try(Connection conn = dbConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM students WHERE id = ?")){
            ps.setInt(1, id);
            int sizeBefore = this.toList().size();
            ps.executeUpdate();
            int sizeAfter = this.toList().size();

            if(sizeBefore > sizeAfter){
                JOptionPane.showConfirmDialog(null, "El estudiante con id " + id + " acaba de ser exitosamente eliminado de nuestra escuela");
            } else {
                JOptionPane.showConfirmDialog(null, "El estudiante con id " + id + " no está inscrito en nuestra escuela");
            }
        }
    }

    private static Student createStudent(ResultSet rs) throws SQLException {
        Student s = new Student();
            s.setId(rs.getInt("id"));
            s.setName(rs.getString("name"));
            s.setLastName(rs.getString("last_name"));
            s.setEmail(rs.getString("email"));
            s.setStatus(Status.valueOf(rs.getString("status")));
        return s;
    }
}
