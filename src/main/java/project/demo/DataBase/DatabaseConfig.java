package project.demo.DataBase;

import javax.swing.*;
import java.sql.*;


import static java.sql.DriverManager.getConnection;

public class DatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/handyman_db";
    private static final String USER = "root";
    private static final String PASS = "";


    public  Connection getConnection() throws SQLException {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connected to database");
            return con;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }
}