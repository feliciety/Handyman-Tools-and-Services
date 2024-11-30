package project.demo.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    // Database credentials and URL
    private static final String URL = "jdbc:mysql://localhost:3306/handyman_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root"; // Replace with your database username
    private static final String PASS = "";    // Replace with your database password

    static {
        try {
            // Attempt to load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Ensure the driver is in the classpath.");
            throw new RuntimeException("Failed to load MySQL JDBC driver", e);
        }
    }

    // Method to establish a connection
    public Connection getConnection() {
        try {
            System.out.println("Connecting to database...");
            Connection connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connected to database successfully!");
            return connection;
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            throw new RuntimeException("Database connection failed", e);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        DatabaseConfig dbConfig = new DatabaseConfig();
        try (Connection connection = dbConfig.getConnection()) {
            System.out.println("Database connection test successful!");
        } catch (Exception e) {
            System.err.println("Database connection test failed: " + e.getMessage());
        }
    }
}
