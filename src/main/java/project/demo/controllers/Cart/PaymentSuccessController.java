package project.demo.controllers.Cart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import project.demo.models.CartItem;
import project.demo.models.Order;
import project.demo.DataBase.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentSuccessController {

    @FXML
    private Label orderIdLabel;

    @FXML
    private Label orderDateLabel;

    @FXML
    private Label shippingMethodLabel;

    @FXML
    private Label paymentMethodLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private TableView<CartItem> productTable;

    @FXML
    private TableColumn<CartItem, String> productNameCol;

    @FXML
    private TableColumn<CartItem, Integer> productQuantityCol;

    @FXML
    private TableColumn<CartItem, Double> productPriceCol;

    @FXML
    private Button backToProfileButton;

    private int orderId; // The order ID passed to this page

    // ObservableList to hold product data
    private final ObservableList<CartItem> productList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize table columns
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Set data into the table view
        productTable.setItems(productList);
    }

    /**
     * Load receipt data for a specific order ID.
     *
     * @param orderId The ID of the completed order.
     */
    public void loadOrderDetails(int orderId) {
        this.orderId = orderId;

        try (Connection connection = new DatabaseConfig().getConnection()) {
            // Fetch order details
            String orderQuery = "SELECT order_date, total_price, shipping_method, payment_method FROM Orders WHERE order_id = ?";
            try (PreparedStatement orderStatement = connection.prepareStatement(orderQuery)) {
                orderStatement.setInt(1, orderId);
                ResultSet orderResult = orderStatement.executeQuery();

                if (orderResult.next()) {
                    // Populate order details
                    orderIdLabel.setText(String.valueOf(orderId));
                    orderDateLabel.setText(orderResult.getTimestamp("order_date").toString());
                    totalPriceLabel.setText("$" + orderResult.getDouble("total_price"));
                    shippingMethodLabel.setText(orderResult.getString("shipping_method"));
                    paymentMethodLabel.setText(orderResult.getString("payment_method"));
                }
            }

            // Fetch product items for the order
            String itemsQuery = "SELECT product_name, quantity, price FROM Order_Items WHERE order_id = ?";
            try (PreparedStatement itemsStatement = connection.prepareStatement(itemsQuery)) {
                itemsStatement.setInt(1, orderId);
                ResultSet itemsResult = itemsStatement.executeQuery();

                while (itemsResult.next()) {
                    // Add each item to the product list
                    CartItem cartItem = new CartItem(
                            itemsResult.getString("product_name"),
                            itemsResult.getInt("quantity"),
                            itemsResult.getDouble("price")
                    );
                    productList.add(cartItem);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigate back to the user's profile page.
     */
    @FXML
    public void goToProfile() {
        // Implement navigation to profile page
        System.out.println("Navigating to Profile Page...");
    }
}