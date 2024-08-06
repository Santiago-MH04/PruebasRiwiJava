package org.santiago.riwi.io.repositories.CRUD;

import java.sql.SQLException;
import java.util.List;

public interface CRUDRepository<T> {
    //Atributos de CRUDRepository
    //Constructores de CRUDRepository
    //Asignadores de atributos de CRUDRepository (setters)
    //Lectores de atributos de CRUDRepository (getters)
        //Métodos de CRUDRepository
    List<T> toList() throws SQLException;
    default T toRetrieve(String field, String search) throws SQLException{
        return null;
    };
    void toSave(T t) throws SQLException;   //Este método guarda o modifica registros dependiendo de si existe el campo id o no
    default void toDelete(int id) throws SQLException{};  //Una nota se elimina de manera recursiva también al eliminar un estudiante, o un curso
}
