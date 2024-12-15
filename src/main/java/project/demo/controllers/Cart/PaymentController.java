package project.demo.controllers.Cart;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import project.demo.controllers.Main.MainStructureController;
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

    private ShippingController shippingController; // Reference to ShippingController

    public void setShippingController(ShippingController shippingController) {
        this.shippingController = shippingController;
    }

    /**
     * Confirm the payment and navigate to the success page.
     */
    public void confirmPayment() {
        try {
            // Data retrieval for the order
            String shippingAddress = "123 Sample Street"; // Example address
            String shippingMethod = "Standard";
            String paymentMethod = "Credit Card";
            double totalPrice = 199.99; // Example total price including shipping
            String shippingNote = "Leave the package at the doorstep.";

            // Example orderId
            int orderId = 1001;

            // Load the PaymentSuccess view into MainStructure content container
            MainStructureController.getInstance().loadPage("/project/demo/FXMLCartPage/PaymentSuccess.fxml");

            // Set order details in the PaymentSuccessController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLCartPage/PaymentSuccess.fxml"));
            loader.load(); // Load the FXML to retrieve its controller

            PaymentSuccessController controller = loader.getController();
            if (controller != null) {
                controller.setOrderDetails(orderId, totalPrice, shippingAddress, shippingMethod, paymentMethod, shippingNote);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ERROR] Payment confirmation failed.");
        }
    }

    private int insertOrder(double shippingFee, String shippingMethod, String shippingNote, String paymentMethod) {
        String query = "INSERT INTO orders (user_id, shipping_address, shipping_fee, shipping_method, shipping_note, payment_method, total_price, order_date) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
        int orderId = -1;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            int userId = UserSession.getInstance().getUserId(); // Get the user ID from session

            stmt.setInt(1, userId); // Set the user_id
            stmt.setString(2, "123 Main Street"); // Replace with dynamic shipping address
            stmt.setDouble(3, shippingFee);       // Set the shipping fee
            stmt.setString(4, shippingMethod);    // Set the shipping method
            stmt.setString(5, shippingNote != null ? shippingNote : ""); // Set the shipping note or default to empty
            stmt.setString(6, paymentMethod);     // Set the payment method
            stmt.setDouble(7, CartManager.getInstance().getTotalPrice() + shippingFee); // Set the total price

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1); // Retrieve the generated order ID
                    System.out.println("[INFO] Order created successfully with ID: " + orderId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to insert order.");
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
            System.out.println("[INFO] Order items inserted successfully. Rows affected: " + rowsInserted.length);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to insert order items.");
        }
    }

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