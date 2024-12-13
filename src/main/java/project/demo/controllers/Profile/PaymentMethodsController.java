package project.demo.controllers.Profile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.demo.dao.CreditCardDAO;
import project.demo.dao.CreditCardDAOImpl;
import project.demo.dao.GCashDAO;
import project.demo.dao.GCashDAOImpl;
import project.demo.dao.PayPalDAO;
import project.demo.dao.PayPalDAOImpl;
import project.demo.models.CreditCard;
import project.demo.models.GCash;
import project.demo.models.PayPal;
import project.demo.models.UserSession;

import java.io.IOException;

public class PaymentMethodsController {

    @FXML
    private Label gcashNumberLabel;

    @FXML
    private Label paypalEmailLabel;

    @FXML
    private Label creditCardNumLabel;

    private final GCashDAO gcashDAO = new GCashDAOImpl();
    private final CreditCardDAO creditCardDAO = new CreditCardDAOImpl();
    private final PayPalDAO payPalDAO = new PayPalDAOImpl();

    // Initialize method to load existing data
    @FXML
    public void initialize() {
        int userId = UserSession.getInstance().getUserId();

        // Load existing GCash data
        GCash gcash = gcashDAO.getGCashByUserId(userId);
        if (gcash != null) {
            gcashNumberLabel.setText(gcash.getPhoneNumber());
        } else {
            gcashNumberLabel.setText("No GCash data available");
        }

        // Load existing CreditCard data
        CreditCard creditCard = creditCardDAO.getCreditCardByUserId(userId);
        if (creditCard != null) {
            creditCardNumLabel.setText(creditCard.getCardNumber());
        } else {
            creditCardNumLabel.setText("No Credit Card data available");
        }

        // Load existing PayPal data
        PayPal payPal = payPalDAO.getPayPalByUserId(userId);
        if (payPal != null) {
            paypalEmailLabel.setText(payPal.getPaypalEmail());
        } else {
            paypalEmailLabel.setText("No PayPal data available");
        }
    }

    // Common method to handle opening popup windows and updating payment method labels
    private <T> void openPaymentPopup(String fxmlPath, String title, T paymentData, PaymentPopupHandler<T> handler) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane popupContent = loader.load();

            if (paymentData instanceof GCash) {
                GCashEditController controller = loader.getController();
                controller.setFields((GCash) paymentData);
            } else if (paymentData instanceof CreditCard) {
                CreditCardEditController controller = loader.getController();
                controller.setFields((CreditCard) paymentData);
            } else if (paymentData instanceof PayPal) {
                PayPalEditController controller = loader.getController();
                controller.setFields((PayPal) paymentData);
            } else {
                throw new IllegalArgumentException("[ERROR] Unsupported payment data type!");
            }

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle(title);
            popupStage.setScene(new Scene(popupContent));
            popupStage.showAndWait();

            handler.handle(); // Execute the callback to update the label
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to load popup: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public void editGCashEditPopup(ActionEvent actionEvent) {
        int userId = UserSession.getInstance().getUserId();
        GCash gcash = gcashDAO.getGCashByUserId(userId);

        // Pass a new GCash object if none exists
        if (gcash == null) {
            gcash = new GCash(0, userId, "", "");
        }

        openPaymentPopup(
                "/project/demo/FXMLProfilePage/PaymentFXML/GCashEditPopup.fxml",
                "Edit GCash Details",
                gcash,
                () -> {
                    GCash updatedGCash = gcashDAO.getGCashByUserId(userId);
                    if (updatedGCash != null) {
                        gcashNumberLabel.setText(updatedGCash.getPhoneNumber());
                        System.out.println("[INFO] GCash details updated successfully.");
                    }
                }
        );
    }


    public void editCreditCardEditPopup(ActionEvent actionEvent) {
        int userId = UserSession.getInstance().getUserId();
        CreditCard creditCard = creditCardDAO.getCreditCardByUserId(userId);

        // Pass a new CreditCard object if none exists
        if (creditCard == null) {
            creditCard = new CreditCard(0, userId, "", "", "", "", "", "");
        }

        openPaymentPopup(
                "/project/demo/FXMLProfilePage/PaymentFXML/CreditCardEditPopup.fxml",
                "Edit Credit Card Details",
                creditCard,
                () -> {
                    CreditCard updatedCard = creditCardDAO.getCreditCardByUserId(userId);
                    if (updatedCard != null) {
                        creditCardNumLabel.setText(updatedCard.getCardNumber());
                        System.out.println("[INFO] Credit Card details updated successfully.");
                    }
                }
        );
    }


    public void editPayPalEditPopup(ActionEvent actionEvent) {
        int userId = UserSession.getInstance().getUserId();
        PayPal payPal = payPalDAO.getPayPalByUserId(userId);

        // Open the popup and handle both updates and new entries
        openPaymentPopup(
                "/project/demo/FXMLProfilePage/PaymentFXML/PayPalEditPopup.fxml",
                "Edit PayPal Details",
                payPal != null ? payPal : new PayPal(userId, "", ""), // Pass a new PayPal instance if null
                () -> {
                    PayPal updatedPayPal = payPalDAO.getPayPalByUserId(userId);
                    if (updatedPayPal != null) {
                        paypalEmailLabel.setText(updatedPayPal.getPaypalEmail());
                        System.out.println("[INFO] PayPal details updated successfully.");
                    } else {
                        System.err.println("[ERROR] Failed to retrieve updated PayPal details.");
                    }
                }
        );
    }


    @FunctionalInterface
    public interface PaymentPopupHandler<T> {
        void handle();
    }
}
