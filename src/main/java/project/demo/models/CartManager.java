package project.demo.models;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CartManager {
    private static CartManager instance;
    private final ObservableList<CartItem> cartItems;

    private CartManager() {
        cartItems = FXCollections.observableArrayList();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public ObservableList<CartItem> getCartItems() {
        return cartItems;
    }

    public void addProductToCart(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProductName().equals(product.getName())) {
                item.incrementQuantity();
                return;
            }
        }
        cartItems.add(new CartItem(product, null));
    }

    public void addCartCounterListener(ChangeListener<Number> listener) {
        cartItems.addListener((javafx.collections.ListChangeListener<CartItem>) change -> {
            listener.changed(null, null, cartItems.size());
        });
    }

    public void clearCart() {
        cartItems.clear();
        System.out.println("[INFO] Cart cleared.");
    }

    public double getTotalPrice() {
        return cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

}
