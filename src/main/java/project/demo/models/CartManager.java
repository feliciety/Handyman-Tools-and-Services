package project.demo.models;

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
}
