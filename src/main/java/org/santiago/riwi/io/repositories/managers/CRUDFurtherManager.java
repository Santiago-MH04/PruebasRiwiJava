package org.santiago.riwi.io.repositories.managers;

import org.santiago.riwi.io.repositories.CRUD.CRUDRepository;

import java.sql.SQLException;
import java.util.List;

public interface CRUDFurtherManager<T> extends CRUDRepository<T> {
    //Atributos de CRUDDeleteExtender
    //Constructores de CRUDDeleteExtender
    //Asignadores de atributos de CRUDDeleteExtender (setters)
    //Lectores de atributos de CRUDDeleteExtender (getters)
        //Métodos de CRUDDeleteExtender
    List<T> toRetrieveList(String field, String search) throws SQLException;
    void toDeleteRec(int course_id, int student_id) throws SQLException;    //Deletes the record, according to the repository that calls it,
                                                                            // either a registration, or a grade
}
