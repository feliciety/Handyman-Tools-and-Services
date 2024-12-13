package project.demo.controllers.Profile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.demo.dao.GCashDAO;
import project.demo.dao.GCashDAOImpl;
import project.demo.models.GCash;
import project.demo.models.UserSession;

public class GCashEditController {
    @FXML
    public Button cancelButton;

    @FXML
    public Button saveButton;

    @FXML
    private TextField gcashAccountField;

    @FXML
    private TextField gcashPhoneField;

    private final GCashDAO gcashDAO = new GCashDAOImpl();
    private GCash gcash;

    public void setFields(GCash gcash) {
        this.gcash = gcash;
        gcashAccountField.setText(gcash.getAccountName() != null ? gcash.getAccountName() : "");
        gcashPhoneField.setText(gcash.getPhoneNumber() != null ? gcash.getPhoneNumber() : "");
    }


    // Saves or updates the GCash account
    @FXML
    public void onSave(ActionEvent actionEvent) {
        int userId = UserSession.getInstance().getUserId(); // Get user ID from session

        // Retrieve data from text fields
        String accountName = gcashAccountField.getText();
        String phoneNumber = gcashPhoneField.getText();

        if (gcash == null) {
            gcash = new GCash(0, userId, accountName, phoneNumber);
        } else {
            gcash.setAccountName(accountName);
            gcash.setPhoneNumber(phoneNumber);
        }

        boolean isSaved = gcashDAO.saveOrUpdateGCashAccount(gcash);

        if (isSaved) {
            System.out.println("GCash details saved successfully!");
            closeWindow();
        } else {
            System.err.println("Error saving GCash details!");
        }
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) gcashAccountField.getScene().getWindow();
        stage.close();
    }

    public void hideButtons() {
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
    }


}
