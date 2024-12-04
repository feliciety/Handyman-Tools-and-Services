package project.demo.controllers.Profile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.demo.dao.CreditCardDAO;
import project.demo.dao.CreditCardDAOImpl;
import project.demo.models.CreditCard;
import project.demo.models.UserSession;

public class CreditCardEditController {

    @FXML
    public Button cancelButton;

    @FXML
    public Button saveButton;

    @FXML
    private TextField cardNameField;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField cvvField;

    @FXML
    private TextField expiryField;

    @FXML
    private TextField billingAddressField;

    @FXML
    private TextField zipCodeField;

    private final CreditCardDAO creditCardDAO = new CreditCardDAOImpl();
    private CreditCard creditCard;

    public void setFields(CreditCard creditCard) {
        this.creditCard = creditCard; // Assign the passed CreditCard object
        if (creditCard != null) {
            cardNameField.setText(creditCard.getCardName());
            cardNumberField.setText(creditCard.getCardNumber());
            cvvField.setText(creditCard.getCvv());
            expiryField.setText(creditCard.getExpiry());
            billingAddressField.setText(creditCard.getBillingAddress());
            zipCodeField.setText(creditCard.getZipCode());
        }
    }

    @FXML
    public void onSave(ActionEvent actionEvent) {
        int userId = UserSession.getInstance().getUserId(); // Get user ID from session

        // Retrieve data from TextFields
        String cardName = cardNameField.getText();
        String cardNumber = cardNumberField.getText();
        String cvv = cvvField.getText();
        String expiry = expiryField.getText();
        String billingAddress = billingAddressField.getText();
        String zipCode = zipCodeField.getText();

        // Create or update the CreditCard record
        CreditCard updatedCreditCard = new CreditCard(0, userId, cardName, cardNumber, cvv, expiry, billingAddress, zipCode);
        boolean isSaved = creditCardDAO.saveOrUpdateCreditCardAccount(updatedCreditCard);

        if (isSaved) {
            System.out.println("Credit card details saved successfully!");
            closeWindow();
        } else {
            System.err.println("Error saving credit card details!");
        }
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) cardNameField.getScene().getWindow();
        stage.close();
    }

    public void hideButtons() {
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
    }


}
