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
import project.demo.models.*;

import java.io.IOException;
import java.util.List;

public class PaymentController {

    @FXML
    private AnchorPane paymentDetailsBox;

    // DAO objects
    private final GCashDAO gcashDAO = new GCashDAOImpl();
    private final CreditCardDAO creditCardDAO = new CreditCardDAOImpl();
    private final PayPalDAO payPalDAO = new PayPalDAOImpl();

    private CartPageController mainController; // Reference to the main controller
    private String selectedPaymentMethod = "COD"; // Default payment method

    public void setMainController(CartPageController mainController) {
        this.mainController = mainController;
        System.out.println("[DEBUG] Main controller set in PaymentController..");
    }

    private ShippingController shippingController; // Reference to ShippingController

    public void setShippingController(ShippingController shippingController) {
        this.shippingController = shippingController;
    }

    /**
     * Confirm the payment and navigate to the success page.
     */
    public void confirmPayment(ActionEvent actionEvent) {
        try {
            String shippingNote = ShippingController.getInstance().getShippingNote(); // Fetch shipping note
            Address selectedAddress = DetailsController.getChosenAddress();

            if (selectedAddress == null) {
                System.err.println("[ERROR] No shipping address selected!");
                return;
            }

            String shippingAddress = selectedAddress.getFullAddress();
            double totalPrice = CartManager.getInstance().getTotalPrice();
            String shippingMethod = ShippingController.getInstance().getSelectedShippingMethod();
            List<CartItem> cartItems = CartManager.getInstance().getCartItems();

            // Save order details including shipping note
            int orderId = OrderManager.saveOrder(
                    UserSession.getInstance().getUserId(),
                    totalPrice,
                    shippingAddress,
                    shippingMethod,
                    selectedPaymentMethod,
                    shippingNote, // Pass the shipping note
                    cartItems
            );

            System.out.println("[INFO] Order saved successfully with ID: " + orderId);

            loadSuccessPage(orderId, totalPrice, shippingAddress, shippingMethod, selectedPaymentMethod, shippingNote);
            CartManager.getInstance().clearCart();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Loads the PaymentSuccess.fxml page with the given order details.
     */
    private void loadSuccessPage(int orderId, double totalPrice, String shippingAddress,
                                 String shippingMethod, String paymentMethod, String shippingNote) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLCartPage/PaymentSuccess.fxml"));
        AnchorPane successPage = loader.load();

        PaymentSuccessController controller = loader.getController();
        controller.setOrderDetails(orderId, totalPrice, shippingAddress, shippingMethod, paymentMethod, "");

        mainController.contentPane.getChildren().setAll(successPage);
    }

    /**
     * Go back to the shipping page.
     */
    @FXML
    public void backToShipping(ActionEvent actionEvent) {
        if (mainController != null) {
            mainController.loadView("/project/demo/FXMLCartPage/Shipping.fxml");
        } else {
            System.err.println("[ERROR] Main controller is not set!");
        }
    }

    /**
     * Select COD payment method.
     */
    @FXML
    public void showCODFields(ActionEvent actionEvent) {
        selectedPaymentMethod = "COD";
        paymentDetailsBox.getChildren().clear();
        System.out.println("[INFO] COD payment selected. No additional fields needed.");
    }

    /**
     * Select GCash payment method.
     */
    @FXML
    public void showGcashFields(ActionEvent actionEvent) {
        selectedPaymentMethod = "GCash";
        loadPaymentDetails("/project/demo/FXMLProfilePage/PaymentFXML/GCashEditPopup.fxml", "GCash");
    }

    /**
     * Select Credit Card payment method.
     */
    @FXML
    public void showCardFields(ActionEvent actionEvent) {
        selectedPaymentMethod = "CreditCard";
        loadPaymentDetails("/project/demo/FXMLProfilePage/PaymentFXML/CreditCardEditPopup.fxml", "CreditCard");
    }

    /**
     * Select PayPal payment method.
     */
    @FXML
    public void showPayPalFields(ActionEvent actionEvent) {
        selectedPaymentMethod = "PayPal";
        loadPaymentDetails("/project/demo/FXMLProfilePage/PaymentFXML/PayPalEditPopup.fxml", "PayPal");
    }

    /**
     * Load specific payment details based on the selected payment method.
     */
    private void loadPaymentDetails(String fxmlPath, String type) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane paymentPane = loader.load();

            Object controller = loader.getController();
            int userId = UserSession.getInstance().getUserId();

            switch (type) {
                case "GCash":
                    GCashEditController gcashController = (GCashEditController) controller;
                    gcashController.setFields(gcashDAO.getGCashByUserId(userId));
                    gcashController.hideButtons();
                    break;

                case "CreditCard":
                    CreditCardEditController cardController = (CreditCardEditController) controller;
                    cardController.setFields(creditCardDAO.getCreditCardByUserId(userId));
                    cardController.hideButtons();
                    break;

                case "PayPal":
                    PayPalEditController paypalController = (PayPalEditController) controller;
                    paypalController.setFields(payPalDAO.getPayPalByUserId(userId));
                    paypalController.hideButtons();
                    break;
            }

            paymentDetailsBox.getChildren().clear();
            paymentDetailsBox.getChildren().add(paymentPane);

        } catch (IOException e) {
            System.err.println("[ERROR] Failed to load payment details FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }

    public String getSelectedPaymentMethod() {
        return selectedPaymentMethod;
    }
}
