package project.demo.dao;

import project.demo.DataBase.DatabaseConfig;
import project.demo.models.PayPal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PayPalDAOImpl implements PayPalDAO {
    private final DatabaseConfig dbConfig = new DatabaseConfig();

    @Override
    public boolean saveOrUpdatePayPalAccount(PayPal payPal) {
        String selectQuery = "SELECT id FROM paypal WHERE user_id = ?";
        String updateQuery = "UPDATE paypal SET paypal_email = ?, alternate_email = ? WHERE user_id = ?";
        String insertQuery = "INSERT INTO paypal (user_id, paypal_email, alternate_email) VALUES (?, ?, ?)";

        try (Connection connection = dbConfig.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {

            selectStatement.setInt(1, payPal.getUserId());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // Update existing record
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setString(1, payPal.getPaypalEmail());
                    updateStatement.setString(2, payPal.getAlternateEmail());
                    updateStatement.setInt(3, payPal.getUserId());
                    updateStatement.executeUpdate();
                    return true;
                }
            } else {
                // Insert new record
                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    insertStatement.setInt(1, payPal.getUserId());
                    insertStatement.setString(2, payPal.getPaypalEmail());
                    insertStatement.setString(3, payPal.getAlternateEmail());
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
    public PayPal getPayPalByUserId(int userId) {
        String query = "SELECT * FROM paypal WHERE user_id = ?";
        try (Connection connection = dbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new PayPal(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("paypal_email"),
                        resultSet.getString("alternate_email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

