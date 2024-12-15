package project.demo.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    // Database credentials and URL
    private static final String URL = "jdbc:mysql://localhost:3306/handyman_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root"; // Replace with your database username
    private static final String PASS = "";     // Replace with your database password

    // Static block to load the MySQL JDBC Driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("[INFO] MySQL JDBC Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("[ERROR] MySQL JDBC Driver not found. Ensure the driver is in the classpath.");
            throw new RuntimeException("Failed to load MySQL JDBC driver", e);
        }
    }

    /**
     * Establishes and returns a connection to the database.
     *
     * @return Connection object
     * @throws RuntimeException if connection fails
     */
    public static Connection getConnection() {
        try {
            System.out.println("[INFO] Connecting to database...");
            Connection connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("[INFO] Connected to database successfully!");
            return connection;
        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to connect to database: " + e.getMessage());
            throw new RuntimeException("Database connection failed", e);
        }
    }

    /**
     * Main method for testing the database connection.
     */
    public static void main(String[] args) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            System.out.println("[INFO] Database connection test successful!");
        } catch (Exception e) {
            System.err.println("[ERROR] Database connection test failed: " + e.getMessage());
        }
    }
}
