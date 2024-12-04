package project.demo.dao;

import project.demo.DataBase.DatabaseConfig;
import project.demo.models.GCash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GCashDAOImpl implements GCashDAO {
    private final DatabaseConfig dbConfig = new DatabaseConfig();

    @Override
    public boolean saveOrUpdateGCashAccount(GCash gcash) {
        String selectQuery = "SELECT id FROM gcash WHERE user_id = ?";
        String updateQuery = "UPDATE gcash SET account_name = ?, phone_number = ? WHERE user_id = ?";
        String insertQuery = "INSERT INTO gcash (user_id, account_name, phone_number) VALUES (?, ?, ?)";

        try (Connection connection = dbConfig.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {

            selectStatement.setInt(1, gcash.getUserId());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // Update existing record
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setString(1, gcash.getAccountName());
                    updateStatement.setString(2, gcash.getPhoneNumber());
                    updateStatement.setInt(3, gcash.getUserId());
                    updateStatement.executeUpdate();
                    return true;
                }
            } else {
                // Insert new record
                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    insertStatement.setInt(1, gcash.getUserId());
                    insertStatement.setString(2, gcash.getAccountName());
                    insertStatement.setString(3, gcash.getPhoneNumber());
                    insertStatement.executeUpdate();
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public GCash getGCashByUserId(int userId) {
        String query = "SELECT * FROM gcash WHERE user_id = ?";
        try (Connection connection = dbConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                GCash gcash = new GCash(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("account_name"),
                        resultSet.getString("phone_number")
                );
                System.out.println("[DEBUG] Retrieved GCash: " + gcash);
                return gcash;
            } else {
                System.err.println("[ERROR] No GCash found for user_id: " + userId);
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to retrieve GCash: " + e.getMessage());
        }
        return null;
    }

}

