package project.demo.controllers.Cart;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import project.demo.models.CartManager;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaymentSuccessController {

    public GridPane orderItemsGridPane;
    public Label totalPriceLabels;
    public Label quantityLabel;
    public Label nameLabel;
    public HBox itemRow;

    @FXML
    private Button backToHomeButton;

    @FXML
    private Label orderIdLabel;

    @FXML
    private Label orderDateLabel;

    @FXML
    private Label shippingMethodLabel;

    @FXML
    private Label shippingAddressLabel;

    @FXML
    private Label shippingNoteLabel;

    @FXML
    private Label paymentMethodLabel;

    @FXML
    private Label totalPriceLabel;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/handyman_db"; // Replace with actual URL
    private static final String DB_USER = "root"; // Replace with actual username
    private static final String DB_PASSWORD = ""; // Replace with actual password

    private static final Logger logger = Logger.getLogger(PaymentSuccessController.class.getName());

    private CartPageController mainController;

    /**
     * Initialize the controller and validate important FXML bindings.
     */
    @FXML
    public void initialize() {
        if (backToHomeButton == null) {
            logger.severe("[ERROR] backToHomeButton is not properly initialized. Check fx:id in FXML.");
        }
        if (orderItemsGridPane == null) {
            logger.severe("[ERROR] orderItemsGridPane is not properly initialized. Check fx:id in FXML.");
        }
    }

    /**
     * Sets order details in the view and dynamically loads the order items into the grid pane.
     *
     * @param orderId         the ID of the confirmed order
     * @param totalPrice      the total price of the order
     * @param shippingAddress the shipping address of the order
     * @param shippingMethod  the shipping method used
     * @param paymentMethod   the payment type used for the order
     * @param shippingNote    the shipping note
     */
    public void setOrderDetails(int orderId, double totalPrice, String shippingAddress,
                                String shippingMethod, String paymentMethod, String shippingNote) {
        // Set order details into their respective labels
        orderIdLabel.setText("#" + orderId);
        orderDateLabel.setText(java.time.LocalDate.now().toString()); // Assume current date
        shippingMethodLabel.setText(shippingMethod);
        shippingAddressLabel.setText(shippingAddress);
        shippingNoteLabel.setText(shippingNote);
        paymentMethodLabel.setText(paymentMethod);
        totalPriceLabel.setText(String.format("₱%.2f", totalPrice));

        if (mainController != null) {
            mainController.hideReceiptPane();
        }
        // Populate the order items for the current order ID
        populateOrderItems(orderId);
        // Clear the cart after placing order
        clearCart();

    }

    /**
     * Dynamically populates the order items belonging to a specific order into the `orderItemsGridPane`.
     *
     * @param orderId the ID of the order whose items will be displayed
     */
    private void populateOrderItems(int orderId) {
        String query = "SELECT product_name, quantity, price FROM order_items WHERE order_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                int rowIndex = 0;

                // Loop through each item and add a row to the grid pane
                while (resultSet.next()) {
                    String productName = resultSet.getString("product_name");
                    int quantity = resultSet.getInt("quantity");
                    double price = resultSet.getDouble("price");
                    double totalPrice = price * quantity;

                    // Load the FXML row for each item
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLCartPage/OrderItemsRow.fxml"));
                    HBox row = loader.load();

                    // Set data into the row's controller
                    OrderItemRowController controller = loader.getController();
                    controller.setOrderItemData(productName, quantity, totalPrice);

                    // Add the populated row to the grid pane
                    orderItemsGridPane.add(row, 0, rowIndex++);
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "[ERROR] Failed to fetch order items from the database", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "[ERROR] Failed to load OrderItemsRow.fxml", e);
        }
    }

    /**
     * Clears the cart after the order has been confirmed.
     */
    private void clearCart() {
        try {
            CartManager.getInstance().clearCart(); // Clears all items from the cart
            System.out.println("[INFO] Cart has been cleared after order confirmation.");
        } catch (Exception e) {
            logger.severe("[ERROR] Failed to clear the cart: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handle the "Back to Home" button click.
     *
     * @param event the action event triggered by the button click
     */
    @FXML
    public void handleBackToHome(ActionEvent event) {
        System.out.println("Redirecting to the home page.");
        // Add navigation logic within your application's structure
    }

    /**
     * Sets the main cart page controller.
     *
     * @param mainController the main controller to set
     */
    public void setMainController(CartPageController mainController) {
        this.mainController = mainController;

        if (mainController != null) {
            AnchorPane thankYouPane = mainController.getThankYouPane();
            if (thankYouPane != null) {
                thankYouPane.setVisible(true); // Show thankYouPane
                System.out.println("[DEBUG] thankYouPane is now set to visible by PaymentSuccessController.");
            } else {
                System.err.println("[ERROR] thankYouPane is null in CartPageController.");
            }
        } else {
            System.err.println("[ERROR] MainController is null. Cannot access thankYouPane.");
        }
    }
}