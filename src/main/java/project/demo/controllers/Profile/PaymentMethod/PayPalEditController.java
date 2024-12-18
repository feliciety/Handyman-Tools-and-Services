package project.demo.controllers.Profile.PaymentMethod;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import project.demo.dao.PayPalDAO;
import project.demo.dao.PayPalDAOImpl;
import project.demo.models.PayPal;
import project.demo.models.UserSession;

public class PayPalEditController extends AbstractFormController<PayPal> {

    @FXML
    private TextField paypalEmailField;

    @FXML
    private TextField alternateEmailField;

    private final PayPalDAO payPalDAO = new PayPalDAOImpl();
    private PayPal payPal;

    @Override
    public void setFields(PayPal payPal) {
        this.payPal = payPal;
        if (payPal != null) {
            paypalEmailField.setText(payPal.getPaypalEmail() != null ? payPal.getPaypalEmail() : "");
            alternateEmailField.setText(payPal.getAlternateEmail() != null ? payPal.getAlternateEmail() : "");
        }
    }

    @Override
    public void onSave(ActionEvent actionEvent) {
        int userId = UserSession.getInstance().getUserId();

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
}