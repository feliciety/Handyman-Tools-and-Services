package project.demo.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import project.demo.controllers.Cart.CartPageController;

public class CartItem {
    private final Product product;
    private final IntegerProperty quantity;
    private final StringProperty totalPrice;
    private int productId;

    private final Button deleteButton;
    private final HBox quantityControl;

    // Constructor for UI Cart Integration
    public CartItem(Product product, CartPageController cartPageController) {
        this.product = product;

        // Initialize quantity to 1 and calculate the total price
        this.quantity = new SimpleIntegerProperty(1);
        this.totalPrice = new SimpleStringProperty(formatPrice());

        // Quantity control logic
        this.quantityControl = createQuantityControl(cartPageController);
        this.deleteButton = createDeleteButton(cartPageController);
    }

    // Constructor for Database Integration
    public CartItem(String productName, int quantity, double price) {
        // Create a minimal Product object for compatibility
        this.product = new Product(productId, productName, price, null);

        this.quantity = new SimpleIntegerProperty(quantity);
        this.totalPrice = new SimpleStringProperty(formatPrice());

        // Disable cart-related buttons since no controller is passed
        this.quantityControl = new HBox();
        this.deleteButton = new Button("X");
        this.deleteButton.setDisable(true); // Delete button disabled for database integration
    }

    // Private helper method to format the price
    private String formatPrice() {
        return String.format("₱%.2f", product.getPrice() * quantity.get());
    }
    private Button createDeleteButton(CartPageController cartPageController) {
        Button button = new Button("X");
        button.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
        button.setOnAction(event -> {
            if (cartPageController != null) {
                cartPageController.removeCartItem(this);
            }
        });
        return button;
    }



    private HBox createQuantityControl(CartPageController cartPageController) {
        Button decreaseButton = new Button("-");
        Label quantityLabel = new Label(String.valueOf(quantity.get()));
        Button increaseButton = new Button("+");

        HBox control = new HBox(decreaseButton, quantityLabel, increaseButton);
        control.setSpacing(5);

        // Decrease button logic
        decreaseButton.setOnAction(event -> {
            if (quantity.get() > 1) {
                decrementQuantity();
                quantityLabel.setText(String.valueOf(quantity.get()));
                if (cartPageController != null) {
                    cartPageController.recalculateSubtotal();
                }
            }
        });

        // Increase button logic
        increaseButton.setOnAction(event -> {
            incrementQuantity();
            quantityLabel.setText(String.valueOf(quantity.get()));
            if (cartPageController != null) {
                cartPageController.recalculateSubtotal();
            }
        });

        return control;
    }


    // Getters for UI rendering
    public ImageView getProductImage() {
        return product.getImageView();
    }

    public String getProductName() {
        return product.getName();
    }


    public HBox getQuantityControl() {
        return quantityControl;
    }

    public String getTotalPrice() {
        return totalPrice.get();
    }

    public StringProperty totalPriceProperty() {
        return totalPrice;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    // Public Methods
    public void incrementQuantity() {
        quantity.set(quantity.get() + 1);
    }

    public void decrementQuantity() {
        if (quantity.get() > 1) {
            quantity.set(quantity.get() - 1);
        }
    }

    // Private Helper Methods
    private void updateTotalPrice() {
        this.totalPrice.set(formatPrice());
    }

}
