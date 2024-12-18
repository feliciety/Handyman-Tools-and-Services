package project.demo.controllers.Profile.PaymentMethod;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import project.demo.controllers.Base.AbstractFormController;
import project.demo.dao.CreditCardDAO;
import project.demo.dao.CreditCardDAOImpl;
import project.demo.models.CreditCard;
import project.demo.models.UserSession;

public class CreditCardEditController extends AbstractFormController<CreditCard> {

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

    @Override
    public void setFields(CreditCard creditCard) {
        this.creditCard = creditCard;
        if (creditCard != null) {
            cardNameField.setText(creditCard.getCardName() != null ? creditCard.getCardName() : "");
            cardNumberField.setText(creditCard.getCardNumber() != null ? creditCard.getCardNumber() : "");
            cvvField.setText(creditCard.getCvv() != null ? creditCard.getCvv() : "");
            expiryField.setText(creditCard.getExpiry() != null ? creditCard.getExpiry() : "");
            billingAddressField.setText(creditCard.getBillingAddress() != null ? creditCard.getBillingAddress() : "");
            zipCodeField.setText(creditCard.getZipCode() != null ? creditCard.getZipCode() : "");
        }
    }

    @Override
    public void onSave(ActionEvent actionEvent) {
        int userId = UserSession.getInstance().getUserId();

        String cardName = cardNameField.getText();
        String cardNumber = cardNumberField.getText();
        String cvv = cvvField.getText();
        String expiry = expiryField.getText();
        String billingAddress = billingAddressField.getText();
        String zipCode = zipCodeField.getText();

        if (creditCard == null) {
            creditCard = new CreditCard(0, userId, cardName, cardNumber, cvv, expiry, billingAddress, zipCode);
        } else {
            creditCard.setCardName(cardName);
            creditCard.setCardNumber(cardNumber);
            creditCard.setCvv(cvv);
            creditCard.setExpiry(expiry);
            creditCard.setBillingAddress(billingAddress);
            creditCard.setZipCode(zipCode);
        }

        boolean isSaved = creditCardDAO.saveOrUpdateCreditCardAccount(creditCard);

        if (isSaved) {
            System.out.println("[INFO] Credit card details saved successfully!");
            closeWindow();
        } else {
            System.err.println("[ERROR] Failed to save credit card details!");
        }
    }
}