package project.demo.dao;

import project.demo.DataBase.DatabaseConfig;
import project.demo.models.CreditCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditCardDAOImpl implements CreditCardDAO {
    private final DatabaseConfig dbConfig = new DatabaseConfig();

    @Override
    public boolean saveOrUpdateCreditCardAccount(CreditCard creditCard) {
        String selectQuery = "SELECT id FROM creditcard WHERE user_id = ?";
        String updateQuery = "UPDATE creditcard SET card_name = ?, card_number = ?, cvv = ?, billing_address = ?, zip_code = ?, expiry = ? WHERE user_id = ?";
        String insertQuery = "INSERT INTO creditcard (user_id, card_name, card_number, cvv, billing_address, zip_code, expiry) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConfig.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {

            selectStatement.setInt(1, creditCard.getUserId());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // Update existing record
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setString(1, creditCard.getCardName());
                    updateStatement.setString(2, creditCard.getCardNumber());
                    updateStatement.setString(3, creditCard.getCvv());
                    updateStatement.setString(4, creditCard.getBillingAddress());
                    updateStatement.setString(5, creditCard.getZipCode());
                    updateStatement.setString(6, creditCard.getExpiry());
                    updateStatement.setInt(7, creditCard.getUserId());
                    updateStatement.executeUpdate();
                    return true;
                }
            } else {
                // Insert new record
                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    insertStatement.setInt(1, creditCard.getUserId());
                    insertStatement.setString(2, creditCard.getCardName());
                    insertStatement.setString(3, creditCard.getCardNumber());
                    insertStatement.setString(4, creditCard.getCvv());
                    insertStatement.setString(5, creditCard.getBillingAddress());
                    insertStatement.setString(6, creditCard.getZipCode());
                    insertStatement.setString(7, creditCard.getExpiry());
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
    public CreditCard getCreditCardByUserId(int userId) {
        String query = "SELECT * FROM creditcard WHERE user_id = ?";
        try (Connection connection = dbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new CreditCard(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("card_name"),
                        resultSet.getString("card_number"),
                        resultSet.getString("cvv"),
                        resultSet.getString("expiry"),
                        resultSet.getString("billing_address"),
                        resultSet.getString("zip_code")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

