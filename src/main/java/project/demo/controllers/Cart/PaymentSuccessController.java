package project.demo.controllers.Cart;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaymentSuccessController {

    @FXML
    private GridPane orderItemsGridPane; // GridPane for dynamically displaying order items

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

    private static final String DB_URL = "jdbc:mysql://localhost:3306/handyman_db"; // Replace with actual database details
    private static final String DB_USER = "root"; // Replace with actual username
    private static final String DB_PASSWORD = ""; // Replace with actual password

    private static final Logger logger = Logger.getLogger(PaymentSuccessController.class.getName());
    private CartPageController mainController;

    /**
     * Initialize the controller and validate important FXML bindings.
     */
    @FXML
    public void initialize() {
        if (orderItemsGridPane == null) {
            logger.severe("[ERROR] orderItemsGridPane is not properly initialized. Check fx:id in FXML.");
        }
    }

    /**
     * Sets order details and dynamically loads the order items into the GridPane.
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
        orderDateLabel.setText(java.time.LocalDate.now().toString()); // Example: current date
        shippingMethodLabel.setText(shippingMethod);
        shippingAddressLabel.setText(shippingAddress);
        shippingNoteLabel.setText(shippingNote);
        paymentMethodLabel.setText(paymentMethod);
        totalPriceLabel.setText(String.format("₱%.2f", totalPrice));

        // Populate the order items for the given orderId
        populateOrderItems(orderId);
    }

    /**
     * Populates the order items belonging to the given order ID into the `orderItemsGridPane`.
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

                // Loop through each item and add it as a row in the grid pane
                while (resultSet.next()) {
                    String productName = resultSet.getString("product_name");
                    int quantity = resultSet.getInt("quantity");
                    double price = resultSet.getDouble("price");
                    double totalPrice = price * quantity;

                    logger.info("Fetched item - Product: " + productName + ", Quantity: " + quantity + ", Total: " + totalPrice);

                    // Load the FXML for each order item row
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLCartPage/OrderItemsRow.fxml"));
                    HBox row = loader.load();

                    // Set the data for the row using the row's controller
                    OrderItemRowController controller = loader.getController();
                    controller.setOrderItemData(productName, quantity, totalPrice);

                    // Add the row to the GridPane
                    orderItemsGridPane.add(row, 0, rowIndex);
                    logger.info("Added row " + rowIndex + " to GridPane for Product: " + productName);

                    rowIndex++;
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "[ERROR] Failed to fetch order items from database", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "[ERROR] Failed to load OrderItemsRow.fxml", e);
        }
    }

    /**
     * Handle the "Back to Home" button click and direct back to the homepage.
     */
    @FXML
    public void handleBackToHome() {
        // Example: Navigating back to the home page
        project.demo.controllers.Main.MainStructureController.getInstance().loadPage("/project/demo/FXMLHomePage/HomePage.fxml");
        logger.info("[INFO] Navigating back to Home page.");
    }
    public void setMainController(CartPageController mainController) {
        this.mainController = mainController;
    }
}