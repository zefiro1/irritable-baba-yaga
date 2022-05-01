package com.irritablebabayaga.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private Connection connection;
    private String url = "jdbc:mariadb://localhost:3306/db_alumnos";
    private String user = "admin";
    private String pass = "admin";


    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void establecerConexion(){
        try {
            connection = DriverManager.getConnection(url,user,pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cerrarConexcion(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
