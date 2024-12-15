package project.demo.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import project.demo.DataBase.DatabaseConfig;

public class OrderManager {

    /**
     * Saves the order and associated cart items to the database.
     *
     * @param userId          The ID of the user placing the order.
     * @param totalPrice      The total price of the order.
     * @param shippingAddress The shipping address for the order.
     * @param shippingMethod  The selected shipping method.
     * @param paymentMethod   The payment method used.
     * @param shippingNote
     * @param cartItems       The list of items in the cart.
     * @return The generated order ID.
     * @throws SQLException If there is an error saving to the database.
     */
    public static int saveOrder(int userId, double totalPrice, String shippingAddress, String shippingMethod, String paymentMethod, String shippingNote, List<CartItem> cartItems) throws SQLException {
        int orderId = -1;

        String insertOrderSQL = "INSERT INTO orders (user_id, total_price, shipping_address, shipping_method, payment_method) VALUES (?, ?, ?, ?, ?)";
        String insertOrderItemSQL = "INSERT INTO order_items (order_id, product_name, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false); // Begin transaction

            // Save the main order and retrieve the generated order ID
            try (PreparedStatement orderStmt = conn.prepareStatement(insertOrderSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, userId);
                orderStmt.setDouble(2, totalPrice);
                orderStmt.setString(3, shippingAddress);
                orderStmt.setString(4, shippingMethod);
                orderStmt.setString(5, paymentMethod);

                int rowsAffected = orderStmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Failed to save order, no rows affected.");
                }

                // Retrieve the generated order ID
                try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Failed to retrieve order ID.");
                    }
                }
            }

            // Save each cart item linked to the order ID
            try (PreparedStatement itemStmt = conn.prepareStatement(insertOrderItemSQL)) {
                for (CartItem item : cartItems) {
                    itemStmt.setInt(1, orderId);
                    itemStmt.setString(2, item.getProductName());
                    itemStmt.setInt(3, item.getQuantity());
                    itemStmt.setDouble(4, item.getProduct().getPrice());
                    itemStmt.addBatch();
                }
                itemStmt.executeBatch(); // Execute batch insert for items
            }

            conn.commit(); // Commit transaction
            System.out.println("[INFO] Order and items saved successfully. Order ID: " + orderId);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to save order: " + e.getMessage());
        }

        return orderId;
    }
}
