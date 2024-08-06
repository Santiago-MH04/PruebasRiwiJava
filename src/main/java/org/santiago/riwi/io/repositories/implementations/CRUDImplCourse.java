package org.santiago.riwi.io.repositories.implementations;

import org.santiago.riwi.io.database.dbConnection;
import org.santiago.riwi.io.models.Course;
import org.santiago.riwi.io.repositories.CRUD.CRUDRepository;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDImplCourse implements CRUDRepository<Course> {
    //Atributos de CRUDImplCourse
        //Constructores de CRUDImplCourse

    public CRUDImplCourse() {
    }

    //Asignadores de atributos de CRUDImplCourse (setters)
    //Lectores de atributos de CRUDImplCourse (getters)
        //Métodos de CRUDImplCourse
    @Override
    public List<Course> toList() throws SQLException {
        List<Course> courses = new ArrayList<>();
            //Obtener la conexión
        try(Connection conn = dbConnection.getConnection()){    //Para autocerrar los recursos
            Statement st = conn.createStatement();
            try(ResultSet RS = st.executeQuery("SELECT * FROM courses")){  //Para autocerrar los recursos
                while(RS.next()){
                    Course course = createCourse(RS);
                    courses.add(course);
                }
            }
        }
        return courses;
    }

    @Override
    public Course toRetrieve(String field, String search) throws SQLException { //Un curso se lo puede buscar tanto por id, como por nombre
        Course course = null;
            //Obtener conexión
        try(Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM students where " + field +" = ?")){
            switch (field){
                case "id" -> ps.setInt(1, Integer.parseInt(search));
                case "name" -> ps.setString(1, search);
            }
            ResultSet RS = ps.executeQuery();
            if(RS.next()){
                course = createCourse(RS);
            }
        }
        return course;
    }

    @Override
    public void toSave(Course course) throws SQLException {
        String sql; //Para definir luego si se guarda, o se modifica un estudiante
        if(course.getId() > 0){    //Para modificar
            sql = "UPDATE courses SET name = ?, attendants = ? WHERE id = ? ";
        } else {    //Para ingresar un nuevo estudiante
            sql = "INSERT INTO courses (name, attendants) VALUES (?,?)";
        }
        try(Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, course.getName());
            ps.setInt(2, course.getAttendants());
            if(course.getId() > 0){
                ps.setInt(3, course.getId());
            }
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                course.setId(rs.getInt(1));
                JOptionPane.showConfirmDialog(null, "El curso con id " + course.getId() + " se impartirá en nuestra escuela");
            } else {
                JOptionPane.showConfirmDialog(null, "La información del curso con id " + course.getId() + " acaba de modificarse exitosamente");
            }
        }
    }

    @Override
    public void toDelete(int id) throws SQLException {
        try(Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM courses WHERE id = ?")){
            ps.setInt(1, id);
            int sizeBefore = this.toList().size();
            ps.executeUpdate();
            int sizeAfter = this.toList().size();

            if(sizeBefore > sizeAfter){
                JOptionPane.showConfirmDialog(null, "El curso con id " + id + " acaba de ser exitosamente eliminado de nuestra escuela");
            } else {
                JOptionPane.showConfirmDialog(null, "El curso con id " + id + " no se imparte en nuestra escuela");
            }
        }
    }

    private static Course createCourse(ResultSet rs) throws SQLException {
        Course c = new Course();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setAttendants(rs.getInt("attendants"));
        return c;
    }
}
