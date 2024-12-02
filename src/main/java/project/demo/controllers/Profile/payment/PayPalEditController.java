package project.demo.controllers.Profile.payment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PayPalEditController {

    @FXML private TextField emailField;
    @FXML private TextField alternateEmailField;

    // Method to save the updated PayPal information
    public void savePayPalInfo() {
        // Add the logic for saving the updated PayPal information to the database
        System.out.println("PayPal Info Saved!");
    }

    // Method to close the popup
    public void closePopup() {
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.close();
    }
    public void onCancel(ActionEvent actionEvent) {
    }

    public void onSave(ActionEvent actionEvent) {

    }

}
