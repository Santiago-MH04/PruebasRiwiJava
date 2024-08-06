package org.santiago.riwi.io.repositories.implementations;

import org.santiago.riwi.io.database.dbConnection;
import org.santiago.riwi.io.models.Course;
import org.santiago.riwi.io.models.Mark;
import org.santiago.riwi.io.models.Student;
import org.santiago.riwi.io.repositories.CRUD.CRUDRepository;
import org.santiago.riwi.io.repositories.managers.CRUDFurtherManager;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDImplMark implements CRUDFurtherManager<Mark> {
        //Atributos de CRUDImplMark
    private static CRUDRepository<Student> studentRepo;
    private static CRUDRepository<Course> courseRepo;

        //Constructores de CRUDImplMark
    public CRUDImplMark() {
        studentRepo = new CRUDImplStudent();
        courseRepo = new CRUDImplCourse();
    }

    //Asignadores de atributos de CRUDImplMark (setters)
    //Lectores de atributos de CRUDImplMark (getters)
        //Métodos de CRUDImplMark

    @Override
    public List<Mark> toList() throws SQLException {
        List<Mark> marks = new ArrayList<>();
        try(Connection conn = dbConnection.getConnection()){    //Para autocerrar los recursos
            Statement st = conn.createStatement();
            try(ResultSet RS = st.executeQuery("SELECT * FROM marks")){  //Para autocerrar los recursos
                while(RS.next()){
                    Mark mark = createMark(RS);
                    marks.add(mark);
                }
            }
        }
        return marks;
    }

    @Override
    public List<Mark> toRetrieveList(String field, String search) throws SQLException {
        List<Mark> marks = new ArrayList<>();
        Mark mark = null;
        try(Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM marks where " + field +" = ?")){
            switch (field){
                case "course_id" -> ps.setInt(1, Integer.parseInt(search));
                case "student_id" -> ps.setInt(1, Integer.parseInt(search));
            }
            ResultSet RS = ps.executeQuery();
            if(RS.next()){
                mark = createMark(RS);
                marks.add(mark);
            }
        }
        return marks;
    }

    @Override
    public void toSave(Mark mark) throws SQLException {
        String sql = "INSERT INTO marks (course_id, student_id, mark) VALUES (?,?,?)";
        try(Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, mark.getCourse().getId());
            ps.setInt(2, mark.getStudent().getId());
            ps.setInt(3, mark.getGrade());

            ps.executeUpdate();
            JOptionPane.showConfirmDialog(null, "La nota del estudiante con id " + mark.getStudent().getId() + " se acaba de registrar exitosamente");
        }
    }

    public void toUpdateMark(Mark mark) throws SQLException {
        String sql = "UPDATE marks SET mark = ? WHERE course_id = ? AND student_id = ?";
        try(Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, mark.getGrade());
            ps.setInt(2, mark.getCourse().getId());
            ps.setInt(3, mark.getStudent().getId());

            ps.executeUpdate();
            JOptionPane.showConfirmDialog(null, "La nota del estudiante con id " + mark.getStudent().getId() + " se acaba de modificar exitosamente");
        }
    }

    @Override
    public void toDeleteRec(int course_id, int student_id) throws SQLException {
        try(Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM marks WHERE course_id = ? AND student_id = ?")){
            ps.setInt(1, course_id);
            ps.setInt(2, student_id);

            int sizeBefore = this.toList().size();
            ps.executeUpdate();
            int sizeAfter = this.toList().size();
            Course course = null;
            Student student = null;
            if(sizeBefore > sizeAfter){
                course = courseRepo.toRetrieve("id", String.valueOf(course_id));
                student = studentRepo.toRetrieve("id", String.valueOf(student_id));

                JOptionPane.showConfirmDialog(null, "La nota del estudiante " + student.getName() + " " + student.getLastName() + " para el curso " + course.getName() + " acaba de borrarse exitosamente");
            } else {
                JOptionPane.showConfirmDialog(null, "El estudiante " + student.getName() + " " + student.getLastName() + " no estaba inscrito en el curso " + course.getName());
            }
        }
    }

    private static Mark createMark(ResultSet rs) throws SQLException {
        Mark m = new Mark();
            m.setGrade(rs.getInt("mark"));

        Course c = courseRepo.toRetrieve("id", Integer.valueOf(rs.getInt("course_id")).toString());
        Student s = studentRepo.toRetrieve("id", Integer.valueOf(rs.getInt("student_id")).toString());
            m.setStudent(s);
            m.setCourse(c);
        return m;
    }
}
