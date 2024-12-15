package project.demo.dao;

import project.demo.models.CartItem;
import project.demo.models.Address;
import project.demo.DataBase.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public int saveOrder(int userId, double totalPrice, Address address, String shippingMethod,
                         String paymentMethod, String shippingNote, List<CartItem> cartItems) throws SQLException {
        int orderId = -1;

        String insertOrderSQL = "INSERT INTO Orders (user_id, total_price, shipping_address, shipping_method, " +
                "payment_method, shipping_note) VALUES (?, ?, ?, ?, ?, ?)";
        String insertOrderItemsSQL = "INSERT INTO Order_Items (order_id, product_name, quantity, price) VALUES (?, ?, ?, ?)";

        String shippingAddress = address.getFullAddress();

        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement orderStmt = conn.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, userId);
                orderStmt.setDouble(2, totalPrice);
                orderStmt.setString(3, shippingAddress);
                orderStmt.setString(4, shippingMethod);
                orderStmt.setString(5, paymentMethod);
                orderStmt.setString(6, shippingNote);
                orderStmt.executeUpdate();

                try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating order failed, no ID obtained.");
                    }
                }
            }

            try (PreparedStatement itemStmt = conn.prepareStatement(insertOrderItemsSQL)) {
                for (CartItem item : cartItems) {
                    itemStmt.setInt(1, orderId);
                    itemStmt.setString(2, item.getProductName());
                    itemStmt.setInt(3, item.getQuantity());
                    itemStmt.setDouble(4, item.getProduct().getPrice());
                    itemStmt.addBatch();
                }
                itemStmt.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return orderId;
    }

    @Override
    public List<CartItem> getOrderItems(int orderId) throws SQLException {
        List<CartItem> orderItems = new ArrayList<>();

        String query = "SELECT product_name, quantity, price FROM Order_Items WHERE order_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String productName = rs.getString("product_name");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");

                    CartItem item = new CartItem(productName, quantity, price);
                    orderItems.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("[ERROR] Failed to retrieve order items: " + e.getMessage());
        }

        return orderItems;
    }
}
