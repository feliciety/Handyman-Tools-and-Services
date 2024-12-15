package project.demo.dao;

import project.demo.models.CartItem;
import project.demo.models.Address;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {
    int saveOrder(int userId, double totalPrice, Address address, String shippingMethod,
                  String paymentMethod, String shippingNote, List<CartItem> cartItems) throws SQLException;

    List<CartItem> getOrderItems(int orderId) throws SQLException;
}
