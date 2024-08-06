package org.santiago.riwi.io;

import org.santiago.riwi.io.database.dbConnection;
import org.santiago.riwi.io.models.Course;
import org.santiago.riwi.io.models.Student;
import org.santiago.riwi.io.repositories.CRUD.CRUDRepository;
import org.santiago.riwi.io.repositories.implementations.CRUDImplCourse;
import org.santiago.riwi.io.repositories.implementations.CRUDImplStudent;

import java.sql.Connection;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        try(Connection conn = dbConnection.getConnection()){    //Es estática, pero no Singleton
            /*CRUDRepository<Student> StudentRepo = new CRUDImplStudent();
                StudentRepo.toList().forEach(System.out::println);
                System.out.println((StudentRepo.toRetrieve("email", "fdiben1@macromedia.com")));*/

            CRUDRepository<Course> CourseRepo = new CRUDImplCourse();
                CourseRepo.toList().forEach(System.out::println);
                System.out.println((CourseRepo.toRetrieve("name", "Latin")));

            System.out.println("Nos conectamos, manito");
        } catch (SQLException e){
            e.printStackTrace();
            System.err.println("No nos conectamos, manito");
        }
    }
}