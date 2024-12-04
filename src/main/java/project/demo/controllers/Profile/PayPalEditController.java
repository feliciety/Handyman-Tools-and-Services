package project.demo.controllers.Profile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.demo.dao.PayPalDAO;
import project.demo.dao.PayPalDAOImpl;
import project.demo.models.PayPal;
import project.demo.models.UserSession;

public class PayPalEditController {

    @FXML
    private TextField paypalEmailField;

    @FXML
    private TextField alternateEmailField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private final PayPalDAO payPalDAO = new PayPalDAOImpl();
    private PayPal payPal;

    /**
     * Sets the fields with data from the PayPal object.
     *
     * @param payPal The PayPal object containing the data.
     */
    public void setFields(PayPal payPal) {
        this.payPal = payPal;
        if (payPal != null) {
            paypalEmailField.setText(payPal.getPaypalEmail());
            alternateEmailField.setText(payPal.getAlternateEmail());
        }
    }

    /**
     * Saves or updates the PayPal account details.
     *
     * @param actionEvent Triggered on Save button click.
     */
    @FXML
    public void onSave(ActionEvent actionEvent) {
        int userId = UserSession.getInstance().getUserId(); // Get user ID from session

        String paypalEmail = paypalEmailField.getText();
        String alternateEmail = alternateEmailField.getText();

        if (payPal == null) {
            payPal = new PayPal(0, userId, paypalEmail, alternateEmail);
        } else {
            payPal.setPaypalEmail(paypalEmail);
            payPal.setAlternateEmail(alternateEmail);
        }

        boolean isSaved = payPalDAO.saveOrUpdatePayPalAccount(payPal);

        if (isSaved) {
            System.out.println("[INFO] PayPal details saved successfully!");
            closeWindow();
        } else {
            System.err.println("[ERROR] Failed to save PayPal details!");
        }
    }

    /**
     * Closes the edit window.
     *
     * @param actionEvent Triggered on Cancel button click.
     */
    @FXML
    public void onCancel(ActionEvent actionEvent) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) paypalEmailField.getScene().getWindow();
        stage.close();
    }

    public void hideButtons() {
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
    }
}
