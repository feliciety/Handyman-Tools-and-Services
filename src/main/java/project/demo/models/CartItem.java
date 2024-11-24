package project.demo.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import project.demo.controllers.CartPageController;

public class CartItem {
    private final Product product;
    private final IntegerProperty quantity;
    private final StringProperty totalPrice;

    private final Button deleteButton;
    private final HBox quantityControl;

    public CartItem(Product product, CartPageController cartPageController) {
        this.product = product;

        // Initialize quantity to 1 and calculate the total price
        this.quantity = new SimpleIntegerProperty(1);
        this.totalPrice = new SimpleStringProperty(formatPrice());

        // Create quantity control
        Button decreaseButton = new Button("-");
        Label quantityLabel = new Label(String.valueOf(quantity.get()));
        Button increaseButton = new Button("+");

        this.quantityControl = new HBox(decreaseButton, quantityLabel, increaseButton);
        this.quantityControl.setSpacing(5);


        // Decrease button logic
        decreaseButton.setOnAction(event -> {
            if (quantity.get() > 1) {
                quantity.set(quantity.get() - 1);
                quantityLabel.setText(String.valueOf(quantity.get())); // Update quantity display
                updateTotalPrice();
                cartPageController.updateTable();
            }
        });

        // Increase button logic
        increaseButton.setOnAction(event -> {
            quantity.set(quantity.get() + 1);
            quantityLabel.setText(String.valueOf(quantity.get())); // Update quantity display
            updateTotalPrice();
            cartPageController.updateTable();
        });

        // Initialize delete button
        // Delete button logic
        this.deleteButton = new Button("Remove");
        deleteButton.setOnAction(event -> {
            if (cartPageController != null) {
                cartPageController.removeCartItem(this);
            }
        });
    }

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
        return quantity.get(); // Add this getter for quantity
    }


    private void updateTotalPrice() {
        this.totalPrice.set(formatPrice());
    }

    private String formatPrice() {
        double pricePerUnit = Double.parseDouble(product.getPrice().replace("$", ""));
        return String.format("$%.2f", pricePerUnit * quantity.get());
    }

    public void incrementQuantity() {
        quantity.set(quantity.get() + 1);
        updateTotalPrice();
    }

    public void decrementQuantity() {
        if (quantity.get() > 1) {
            quantity.set(quantity.get() - 1);
            updateTotalPrice();
        }
    }
}
