package project.demo.dao;

import project.demo.models.PaymentMethod;
import project.demo.models.CreditCard;
import project.demo.models.PayPal;
import project.demo.models.GCash;
import project.demo.DataBase.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentMethodDAO {

    private final DatabaseConfig dbConfig;

    public PaymentMethodDAO() {
        dbConfig = new DatabaseConfig();
    }

    // Method to get all payment methods for a user
    public List<PaymentMethod> getPaymentMethods(int userId) {
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        String sql = "SELECT * FROM user_payment_methods JOIN credit_card_details ON user_payment_methods.id = credit_card_details.user_payment_method_id WHERE user_payment_methods.user_id = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String paymentMethodType = rs.getString("payment_method_type");
                if ("Credit Card".equals(paymentMethodType)) {
                    paymentMethods.add(new CreditCard(
                            rs.getInt("id"),
                            userId,
                            rs.getString("name_on_card"),
                            rs.getString("card_number"),
                            rs.getString("expiry_date"),
                            rs.getString("cvv"),
                            rs.getString("billing_address"),
                            rs.getString("zip_code")
                    ));
                } else if ("PayPal".equals(paymentMethodType)) {
                    paymentMethods.add(new PayPal(
                            rs.getInt("id"),
                            userId,
                            rs.getString("email"),
                            rs.getString("alternate_email")
                    ));
                } else if ("GCash".equals(paymentMethodType)) {
                    paymentMethods.add(new GCash(
                            rs.getInt("id"),
                            userId,
                            rs.getString("phone_number"),
                            rs.getString("account_number")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentMethods;
    }

    // Method to update payment method details
    public void updatePaymentMethod(PaymentMethod paymentMethod) {
        String sql = "UPDATE payment_methods SET details = ? WHERE id = ?";

        try (Connection conn = dbConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, paymentMethod.getDetails());
            pstmt.setInt(2, paymentMethod.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a payment method
    public void deletePaymentMethod(int userId, int paymentMethodId, String tableName) {
        String sql = "DELETE FROM user_payment_methods WHERE user_id = ? AND payment_method_id = ?";
        try (Connection conn = dbConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, paymentMethodId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGCashInfo(int userId, String newPhoneNumber, String newAccountNumber) {
        String sql = "UPDATE gcash_details SET phone_number = ?, account_number = ? " +
                "WHERE user_payment_method_id IN (SELECT id FROM user_payment_methods WHERE user_id = ?)";

        try (Connection conn = dbConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPhoneNumber);
            pstmt.setString(2, newAccountNumber);
            pstmt.setInt(3, userId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("GCash information updated successfully.");
            } else {
                System.out.println("Failed to update GCash information.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updatePayPalInfo(int userId, String newEmail, String newAlternateEmail) {
        String sql = "UPDATE paypal_details SET email = ?, alternate_email = ? " +
                "WHERE user_payment_method_id IN (SELECT id FROM user_payment_methods WHERE user_id = ?)";

        try (Connection conn = dbConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newEmail);
            pstmt.setString(2, newAlternateEmail);
            pstmt.setInt(3, userId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("PayPal information updated successfully.");
            } else {
                System.out.println("Failed to update PayPal information.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateCreditCardInfo(int userId, String newNameOnCard, String newCardNumber, String newExpiryDate, String newCvv, String newBillingAddress, String newZipCode) {
        String sql = "UPDATE credit_card_details SET name_on_card = ?, card_number = ?, expiry_date = ?, cvv = ?, billing_address = ?, zip_code = ? " +
                "WHERE user_payment_method_id IN (SELECT id FROM user_payment_methods WHERE user_id = ?)";

        try (Connection conn = dbConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newNameOnCard);
            pstmt.setString(2, newCardNumber);
            pstmt.setString(3, newExpiryDate);
            pstmt.setString(4, newCvv);
            pstmt.setString(5, newBillingAddress);
            pstmt.setString(6, newZipCode);
            pstmt.setInt(7, userId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Credit Card information updated successfully.");
            } else {
                System.out.println("Failed to update Credit Card information.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
