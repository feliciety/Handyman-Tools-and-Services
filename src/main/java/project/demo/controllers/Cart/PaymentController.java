package project.demo.controllers.Cart;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class PaymentController {

    @FXML
    private AnchorPane paymentDetailsBox;

    // DAO objects
    private final GCashDAO gcashDAO = new GCashDAOImpl();
    private final CreditCardDAO creditCardDAO = new CreditCardDAOImpl();
    private final PayPalDAO payPalDAO = new PayPalDAOImpl();

    private static final String DB_URL = "jdbc:mysql://localhost:3306/handyman_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private CartPageController mainController; // Reference to the main controller
    private String selectedPaymentMethod = "COD"; // Default payment method

    public void setMainController(CartPageController mainController) {
        this.mainController = mainController;
        System.out.println("[DEBUG] Main controller set in PaymentController..");
    }
    

    /**
     * Confirm the payment and navigate to the success page.
     */
    public void confirmPayment(ActionEvent actionEvent) {
        try {
            Address shippingAddress = DetailsController.getChosenAddress();
            String shippingMethod = ShippingController.getInstance().getSelectedShippingMethod();
            double shippingFee = ShippingController.getInstance().getShippingFee();
            String shippingNote = ShippingController.getInstance().getShippingNote();
            int userId = UserSession.getInstance().getUserId();

            System.out.println("[DEBUG] User ID: " + userId);
            System.out.println("[DEBUG] Shipping Address: " + shippingAddress.getFullAddress());

            int orderId = insertOrder(userId, shippingAddress.getFullAddress(), shippingFee, shippingMethod, shippingNote);

            if (orderId > 0) {
                insertOrderItems(orderId);
                navigateToSuccessPage(orderId, shippingFee, shippingMethod, shippingNote, selectedPaymentMethod);
            } else {
                System.err.println("[ERROR] Order creation failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int insertOrder(int userId, String shippingAddress, double shippingFee, String shippingMethod, String shippingNote) {
        String query = "INSERT INTO orders (user_id, shipping_address, shipping_fee, shipping_method, shipping_note, payment_method, total_price, order_date) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
        int orderId = -1;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, userId);
            stmt.setString(2, shippingAddress);
            stmt.setDouble(3, shippingFee);
            stmt.setString(4, shippingMethod);
            stmt.setString(5, shippingNote);
            stmt.setString(6, selectedPaymentMethod);
            stmt.setDouble(7, CartManager.getInstance().getTotalPrice() + shippingFee);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1);
                    System.out.println("[INFO] Order created successfully. Order ID: " + orderId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderId;
    }

    private void insertOrderItems(int orderId) {
        String query = "INSERT INTO order_items (order_id, product_name, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (CartItem item : CartManager.getInstance().getCartItems()) {
                stmt.setInt(1, orderId);
                stmt.setString(2, item.getProduct().getName());
                stmt.setInt(3, item.getQuantity());
                stmt.setDouble(4, item.getProduct().getPrice());
                stmt.addBatch();
            }

            int[] rowsInserted = stmt.executeBatch();
            System.out.println("[INFO] Order items inserted. Rows affected: " + rowsInserted.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateToSuccessPage(int orderId, double shippingFee, String shippingMethod, String shippingNote, String paymentMethod) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLCartPage/PaymentSuccess.fxml"));
            Parent successView = loader.load();

            PaymentSuccessController controller = loader.getController();
            controller.setOrderDetails(
                    orderId,
                    CartManager.getInstance().getTotalPrice() + shippingFee,
                    DetailsController.getChosenAddress().getFullAddress(),
                    shippingMethod,
                    paymentMethod,
                    shippingNote
            );

            mainController.getContentPane().getChildren().setAll(successView);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to load PaymentSuccess.fxml");
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

    private AnchorPane contentPane; // Must match fx:id in FXML

    @FXML
    public void backToShipping(ActionEvent actionEvent) {
        mainController.goToShipping();
    }
}