package org.santiago.riwi.io.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    /*private static String URL = "jdbc:mysql://<hostname>:<port>/<database>?serverTimezone=America/Bogota";*/  //Los elementos en <> se deben cambiar, incluyendo al operador diamante
    private static String url = "jdbc:mysql://localhost:3306/RiwiAcademyDB?serverTimezone=America/Bogota";
    private static String username = "root";
    private static String password = "tupassword";

    /*private Connection conn;*/

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
