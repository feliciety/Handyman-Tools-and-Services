package project.demo.controllers.Profile.payment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreditCardEditController {

    @FXML private TextField nameOnCardField;
    @FXML private TextField cardNumberField;
    @FXML private TextField expiryField;
    @FXML private TextField cvvField;
    @FXML private TextField billingAddressField;
    @FXML private TextField zipCodeField;

    // Method to save the updated credit card information
    public void saveCreditCardInfo() {
        // Add the logic for saving the updated information to the database
        System.out.println("Credit Card Info Saved!");
    }

    // Method to close the popup
    public void closePopup() {
        Stage stage = (Stage) nameOnCardField.getScene().getWindow();
        stage.close();
    }

    public void onCancel(ActionEvent actionEvent) {
    }

    public void onSave(ActionEvent actionEvent) {

    }
}
