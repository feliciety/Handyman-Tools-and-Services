package project.demo.models;

import project.demo.models.UserSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserData {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    public static void loadUserDataIntoSession(int userId) {
        String query = "SELECT username, email, contact FROM users WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UserSession session = UserSession.getInstance();
                session.setUsername(rs.getString("username"));
                session.setEmail(rs.getString("email"));
                session.setContactNumber(rs.getString("contact"));
                session.setUserId(userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
