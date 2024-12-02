package project.demo.controllers.Profile;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.demo.DataBase.DatabaseConfig;
import project.demo.models.CreditCard;
import project.demo.models.PayPal;
import project.demo.models.GCash;
import project.demo.models.PaymentMethod;
import project.demo.dao.PaymentMethodDAO;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditPaymentMethodsController {

    private final DatabaseConfig dbConfig = new DatabaseConfig();
    private final PaymentMethodDAO paymentMethodDAO = new PaymentMethodDAO();

    // --- Delete Payment Method ---

    public void deletePaymentMethod(int userId, int paymentMethodId, String tableName) {
        // Dynamically construct the DELETE query based on the table name
        String sql = "DELETE FROM " + tableName + " WHERE user_payment_method_id = ? AND user_id = ?";

        try (Connection conn = dbConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, paymentMethodId);  // Set the payment method ID
            pstmt.setInt(2, userId);  // Set the user ID
            int rowsAffected = pstmt.executeUpdate();  // Execute the query

            if (rowsAffected > 0) {
                System.out.println("Payment method deleted successfully.");
            } else {
                System.out.println("Failed to delete payment method.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // --- Edit Payment Method (Generic) ---

    public void editPaymentMethod(ActionEvent actionEvent, int userId, String paymentMethodType) {
        switch (paymentMethodType) {
            case "Credit Card":
                editCreditCard(actionEvent, userId);
                break;
            case "PayPal":
                editPayPal(actionEvent, userId);
                break;
            case "GCash":
                editGCash(actionEvent, userId);
                break;
            default:
                showAlert(AlertType.ERROR, "Error", "Unknown payment method.");
        }
    }

    // --- Edit Specific Payment Methods ---

    // Edit Credit Card payment method
    private void editCreditCard(ActionEvent actionEvent, int userId) {
        String sql = "SELECT * FROM credit_card_details WHERE user_payment_method_id IN (SELECT id FROM user_payment_methods WHERE user_id = ?)";
        try (Connection conn = dbConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Retrieve and populate fields with Credit Card details
                String nameOnCard = rs.getString("name_on_card");
                String cardNumber = rs.getString("card_number");
                String expiryDate = rs.getString("expiry_date");
                String cvv = rs.getString("cvv");
                String billingAddress = rs.getString("billing_address");
                String zipCode = rs.getString("zip_code");

                // Populate UI fields (this should be in your UI layer)
                // Example: cardNameField.setText(nameOnCard);
            } else {
                showAlert(AlertType.ERROR, "Error", "No Credit Card information found for this user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "An error occurred while fetching Credit Card information.");
        }
    }

    // Edit PayPal payment method
    private void editPayPal(ActionEvent actionEvent, int userId) {
        String sql = "SELECT * FROM paypal_details WHERE user_payment_method_id IN (SELECT id FROM user_payment_methods WHERE user_id = ?)";
        try (Connection conn = dbConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Retrieve and populate fields with PayPal details
                String email = rs.getString("email");
                String alternateEmail = rs.getString("alternate_email");

                // Populate UI fields (this should be in your UI layer)
                // Example: paypalEmailField.setText(email);
            } else {
                showAlert(AlertType.ERROR, "Error", "No PayPal information found for this user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "An error occurred while fetching PayPal information.");
        }
    }

    // Edit GCash payment method
    private void editGCash(ActionEvent actionEvent, int userId) {
        String sql = "SELECT * FROM gcash_details WHERE user_payment_method_id IN (SELECT id FROM user_payment_methods WHERE user_id = ?)";
        try (Connection conn = dbConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Retrieve and populate fields with GCash details
                String phoneNumber = rs.getString("phone_number");
                String accountNumber = rs.getString("account_number");

                // Populate UI fields (this should be in your UI layer)
                // Example: gcashPhoneField.setText(phoneNumber);
            } else {
                showAlert(AlertType.ERROR, "Error", "No GCash information found for this user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "An error occurred while fetching GCash information.");
        }
    }

    // --- Update Payment Method ---

    // Method to update Credit Card info
    public void updateCreditCardInfo(ActionEvent actionEvent, int userId, String newNameOnCard, String newCardNumber, String newExpiryDate, String newCvv, String newBillingAddress, String newZipCode) {
        paymentMethodDAO.updateCreditCardInfo(userId, newNameOnCard, newCardNumber, newExpiryDate, newCvv, newBillingAddress, newZipCode);
    }

    // Method to update PayPal info
    public void updatePayPalInfo(ActionEvent actionEvent, int userId, String newEmail, String newAlternateEmail) {
        paymentMethodDAO.updatePayPalInfo(userId, newEmail, newAlternateEmail);
    }

    // Method to update GCash info
    public void updateGCashInfo(ActionEvent actionEvent, int userId, String newPhoneNumber, String newAccountNumber) {
        paymentMethodDAO.updateGCashInfo(userId, newPhoneNumber, newAccountNumber);
    }

    // --- Show Alert Messages ---
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // --- Dummy User ID Function (Replace with actual authentication) ---
    private int getCurrentUserId() {
        return 1;  // Dummy ID for testing purposes
    }

    // --- Button Handlers for Delete ---
    // Delete Payment Method (Generic)
    public void deletePaymentMethod(ActionEvent actionEvent, int userId, int paymentMethodId, String tableName) {
        paymentMethodDAO.deletePaymentMethod(userId, paymentMethodId, tableName);
    }

    // Example calls in the controller
    public void onDeleteCreditCard(ActionEvent actionEvent) {
        int userId = getCurrentUserId();
        int paymentMethodId = 1; // Set the correct payment method ID for Credit Card
        deletePaymentMethod(actionEvent, userId, paymentMethodId, "credit_card_details");
    }

    public void onDeletePaypal(ActionEvent actionEvent) {
        int userId = getCurrentUserId();
        int paymentMethodId = 2; // Set the correct payment method ID for PayPal
        deletePaymentMethod(actionEvent, userId, paymentMethodId, "paypal_details");
    }

    public void onDeleteGcash(ActionEvent actionEvent) {
        int userId = getCurrentUserId();
        int paymentMethodId = 3; // Set the correct payment method ID for GCash
        deletePaymentMethod(actionEvent, userId, paymentMethodId, "gcash_details");
    }


        // Method to open the Credit Card edit popup
        public void openCreditCardEditPopup(ActionEvent actionEvent) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLProfilePage/PaymentFXML/CreditCardEditPopup.fxml"));
            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Edit Credit Card");
            stage.setScene(new Scene(root));
            stage.show();
        }

        // Method to open the PayPal edit popup
        public void openPayPalEditPopup(ActionEvent actionEvent) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLProfilePage/PaymentFXML/PayPalEditPopup.fxml"));
            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Edit PayPal");
            stage.setScene(new Scene(root));
            stage.show();
        }

        // Method to open the GCash edit popup
        public void openGCashEditPopup(ActionEvent actionEvent) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLProfilePage/PaymentFXML/GCashEditPopup.fxml"));
            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Edit GCash");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }


