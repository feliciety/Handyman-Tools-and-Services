package project.demo.controllers.Profile.PaymentMethod;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import project.demo.dao.GCashDAO;
import project.demo.dao.GCashDAOImpl;
import project.demo.models.GCash;
import project.demo.models.UserSession;

public class GCashEditController extends AbstractFormController<GCash> {

    @FXML
    private TextField gcashAccountField;

    @FXML
    private TextField gcashPhoneField;

    private final GCashDAO gcashDAO = new GCashDAOImpl();
    private GCash gcash;

    @Override
    public void setFields(GCash gcash) {
        this.gcash = gcash;
        if (gcash != null) {
            gcashAccountField.setText(gcash.getAccountName() != null ? gcash.getAccountName() : "");
            gcashPhoneField.setText(gcash.getPhoneNumber() != null ? gcash.getPhoneNumber() : "");
        }
    }

    @Override
    public void onSave(ActionEvent actionEvent) {
        int userId = UserSession.getInstance().getUserId();

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
            System.out.println("[INFO] GCash details saved successfully!");
            closeWindow();
        } else {
            System.err.println("[ERROR] Failed to save GCash details!");
        }
    }
}