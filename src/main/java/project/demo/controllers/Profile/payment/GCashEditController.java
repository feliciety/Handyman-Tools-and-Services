package project.demo.controllers.Profile.payment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GCashEditController {

    @FXML private TextField phoneNumberField;
    @FXML private TextField accountNumberField;

    // Method to save the updated GCash information
    public void saveGCashInfo() {
        // Add the logic for saving the updated GCash information to the database
        System.out.println("GCash Info Saved!");
    }

    // Method to close the popup
    public void closePopup() {
        Stage stage = (Stage) phoneNumberField.getScene().getWindow();
        stage.close();
    }
    public void onCancel(ActionEvent actionEvent) {
    }

    public void onSave(ActionEvent actionEvent) {

    }
}
