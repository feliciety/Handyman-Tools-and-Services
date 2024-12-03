package project.demo.controllers.Profile.payment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.demo.models.PayPal;
import project.demo.dao.PaymentMethodDAO;

public class PayPalEditController {

    @FXML private TextField emailField;
    @FXML private TextField alternateEmailField;

    private PayPal payPal;
    private PaymentMethodDAO paymentMethodDAO;

    // Constructor to initialize PaymentMethodDAO
    public PayPalEditController() {
        paymentMethodDAO = new PaymentMethodDAO();  // Initialize the DAO for saving the data
    }

    // Set PayPal object for editing
    public void setPayPal(PayPal payPal) {
        this.payPal = payPal;
        if (payPal != null) {
            emailField.setText(payPal.getEmail());
            alternateEmailField.setText(payPal.getAlternateEmail());
        }
    }

    // Method to get the updated PayPal information
    public PayPal getUpdatedPayPal() {
        if (payPal != null) {
            payPal.setEmail(emailField.getText());
            payPal.setAlternateEmail(alternateEmailField.getText());
        }
        return payPal;
    }

    // Method to save the updated PayPal information
    public void savePayPalInfo() {
        PayPal updatedPayPal = getUpdatedPayPal();

        if (updatedPayPal != null && validateInput(updatedPayPal)) {
            // Save to the database
            boolean isSaved = paymentMethodDAO.updatePayPalInfo(updatedPayPal);

            if (isSaved) {
                showAlert(AlertType.INFORMATION, "Success", "PayPal information saved successfully!");
                closePopup();
            } else {
                showAlert(AlertType.ERROR, "Error", "Failed to save PayPal information.");
            }
        } else {
            showAlert(AlertType.WARNING, "Validation Failed", "Please fill in valid details.");
        }
    }

    // Method to validate the input fields
    private boolean validateInput(PayPal payPal) {
        return payPal.getEmail() != null && !payPal.getEmail().isEmpty() &&
                payPal.getAlternateEmail() != null && !payPal.getAlternateEmail().isEmpty();
    }

    // Method to show alert dialogs
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to close the popup
    public void closePopup() {
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.close();
    }

    // Event handler for the "Cancel" button
    public void onCancel(ActionEvent actionEvent) {
        closePopup();  // Simply close the popup when Cancel is pressed
    }

    // Event handler for the "Save" button
    public void onSave(ActionEvent actionEvent) {
        savePayPalInfo();  // Call the method to save PayPal information
    }
}
