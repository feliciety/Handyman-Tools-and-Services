package project.demo.controllers.Booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import project.demo.controllers.Base.AbstractFormController;
import project.demo.dao.*;
import project.demo.models.CreditCard;
import project.demo.models.GCash;
import project.demo.models.PayPal;
import project.demo.models.UserSession;

import java.io.IOException;

public class BookingPaymentController {

    @FXML
    private AnchorPane paymentDetailsBox;

    // DAO objects
    private final GCashDAO gcashDAO = new GCashDAOImpl();
    private final CreditCardDAO creditCardDAO = new CreditCardDAOImpl();
    private final PayPalDAO payPalDAO = new PayPalDAOImpl();

    private BookingPageController mainController; // Reference to the main controller

    public void setMainController(BookingPageController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void showGcashFields(ActionEvent actionEvent) {
        loadPaymentDetails("/project/demo/FXMLProfilePage/PaymentFXML/GCashEditPopup.fxml", "GCash");
    }

    @FXML
    public void showCardFields(ActionEvent actionEvent) {
        loadPaymentDetails("/project/demo/FXMLProfilePage/PaymentFXML/CreditCardEditPopup.fxml", "CreditCard");
    }

    @FXML
    public void showPayPalFields(ActionEvent actionEvent) {
        loadPaymentDetails("/project/demo/FXMLProfilePage/PaymentFXML/PayPalEditPopup.fxml", "PayPal");
    }

    @FXML
    public void showCODFields(ActionEvent actionEvent) {
        paymentDetailsBox.getChildren().clear();
        System.out.println("COD payment selected. No additional fields needed.");
    }

    private void loadPaymentDetails(String fxmlPath, String type) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane paymentPane = loader.load();

            AbstractFormController<?> controller = loader.getController();
            int userId = UserSession.getInstance().getUserId();

            // Populate fields based on payment type using AbstractFormController
            switch (type) {
                case "GCash":
                    GCash existingGCash = gcashDAO.getGCashByUserId(userId);
                    if (controller instanceof AbstractFormController<?>) {
                        AbstractFormController<GCash> gcashController = (AbstractFormController<GCash>) controller;
                        gcashController.setFields(existingGCash);
                        gcashController.hideButtons();
                    }
                    break;
                case "CreditCard":
                    CreditCard existingCard = creditCardDAO.getCreditCardByUserId(userId);
                    if (controller instanceof AbstractFormController<?>) {
                        AbstractFormController<CreditCard> cardController = (AbstractFormController<CreditCard>) controller;
                        cardController.setFields(existingCard);
                        cardController.hideButtons();
                    }
                    break;
                case "PayPal":
                    PayPal existingPayPal = payPalDAO.getPayPalByUserId(userId);
                    if (controller instanceof AbstractFormController<?>) {
                        AbstractFormController<PayPal> paypalController = (AbstractFormController<PayPal>) controller;
                        paypalController.setFields(existingPayPal);
                        paypalController.hideButtons();
                    }
                    break;
                default:
                    throw new IllegalArgumentException("[ERROR] Unsupported payment data type!");
            }

            paymentDetailsBox.getChildren().clear();
            paymentDetailsBox.getChildren().add(paymentPane);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to load payment details FXML: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public void backToAddressBookingDetails(ActionEvent actionEvent) {
        if (mainController != null) {
            mainController.loadView("/project/demo/FXMLBookingPage/AddressBookingDetails.fxml");
        } else {
            System.err.println("Main controller is not set!");
        }
    }

    @FXML
    public void confirmPayment(ActionEvent actionEvent) {
        if (mainController != null) {
            System.out.println("Navigating to Payment Success view...");
            mainController.loadView("/project/demo/FXMLBookingPage/BookingPaymentSuccess.fxml");
        } else {
            System.err.println("Main controller is not set!");
        }
    }
}