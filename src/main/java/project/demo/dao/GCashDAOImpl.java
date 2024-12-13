package project.demo.dao;

import project.demo.models.GCash;
import project.demo.DataBase.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GCashDAOImpl implements GCashDAO {

    @Override
    public boolean saveOrUpdateGCashAccount(GCash gcash) {
        String query;
        boolean isUpdate = doesGCashExist(gcash.getUserId()); // Check if a GCash account already exists

        if (isUpdate) {
            query = "UPDATE GCash SET account_name = ?, phone_number = ? WHERE user_id = ?";
        } else {
            query = "INSERT INTO GCash (user_id, account_name, phone_number) VALUES (?, ?, ?)";
        }

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            if (isUpdate) {
                statement.setString(1, gcash.getAccountName());
                statement.setString(2, gcash.getPhoneNumber());
                statement.setInt(3, gcash.getUserId());
            } else {
                statement.setInt(1, gcash.getUserId());
                statement.setString(2, gcash.getAccountName());
                statement.setString(3, gcash.getPhoneNumber());
            }

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            System.err.println("[ERROR] Failed to save or update GCash account: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    private boolean doesGCashExist(int userId) {
        String query = "SELECT 1 FROM GCash WHERE user_id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Returns true if a record exists
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to check GCash existence: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public GCash getGCashByUserId(int userId) {
        String query = "SELECT id, user_id, account_name, phone_number FROM GCash WHERE user_id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new GCash(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("account_name"),
                        resultSet.getString("phone_number")
                );
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to fetch GCash by user ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
