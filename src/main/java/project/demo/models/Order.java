package project.demo.models;

import project.demo.models.CartItem;

import java.sql.Timestamp;
import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private Timestamp orderDate;
    private double totalPrice;
    private String shippingMethod;
    private String paymentMethod;
    private List<CartItem> items;
    private String shippingNote;


    // Constructor
    public Order(int orderId, int userId, Timestamp orderDate, double totalPrice,
                 String shippingMethod, String paymentMethod, List<CartItem> items) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.shippingMethod = shippingMethod;
        this.paymentMethod = paymentMethod;
        this.items = items;
    }

    // Getters and Setters

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public String getShippingNote() {
        return shippingNote;
    }

    public void setShippingNote(String shippingNote) {
        this.shippingNote = shippingNote;
    }


    // toString for debugging
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", orderDate=" + orderDate +
                ", totalPrice=" + totalPrice +
                ", shippingMethod='" + shippingMethod + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", items=" + items +
                '}';
    }
}