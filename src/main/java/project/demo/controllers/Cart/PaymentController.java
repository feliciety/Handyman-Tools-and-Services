package project.demo.controllers.Cart;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import project.demo.controllers.Profile.CreditCardEditController;
import project.demo.controllers.Profile.GCashEditController;
import project.demo.controllers.Profile.PayPalEditController;
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

public class PaymentController {

    @FXML
    private AnchorPane paymentDetailsBox;

    // DAO objects
    private final GCashDAO gcashDAO = new GCashDAOImpl();
    private final CreditCardDAO creditCardDAO = new CreditCardDAOImpl();
    private final PayPalDAO payPalDAO = new PayPalDAOImpl();

    private CartPageController mainController; // Reference to the main controller

    public void setMainController(CartPageController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void confirmPayment(ActionEvent actionEvent) {
        if (mainController != null) {
            System.out.println("Navigating to Payment Success view...");
            mainController.loadView("/project/demo/FXMLCartPage/PaymentSuccess.fxml");
        } else {
            System.err.println("Main controller is not set!");
        }
    }

    @FXML
    public void backToShipping(ActionEvent actionEvent) {
        if (mainController != null) {
            System.out.println("Navigating back to Shipping view...");
            mainController.loadView("project/demo/FXMLCartPage/Shipping.fxml");
        } else {
            System.err.println("Main controller is not set!");
        }
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

            Object controller = loader.getController();
            int userId = UserSession.getInstance().getUserId();

            // Populate fields based on payment type
            switch (type) {
                case "GCash":
                    if (controller instanceof GCashEditController) {
                        GCashEditController gcashController = (GCashEditController) controller;
                        GCash existingGCash = gcashDAO.getGCashByUserId(userId);
                        gcashController.setFields(existingGCash);
                        gcashController.hideButtons();
                    }
                    break;
                case "CreditCard":
                    if (controller instanceof CreditCardEditController) {
                        CreditCardEditController cardController = (CreditCardEditController) controller;
                        CreditCard existingCard = creditCardDAO.getCreditCardByUserId(userId);
                        cardController.setFields(existingCard);
                        cardController.hideButtons();
                    }
                    break;
                case "PayPal":
                    if (controller instanceof PayPalEditController) {
                        PayPalEditController paypalController = (PayPalEditController) controller;
                        PayPal existingPayPal = payPalDAO.getPayPalByUserId(userId);
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
}
