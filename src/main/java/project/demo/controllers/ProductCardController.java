package project.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.demo.models.CartManager;
import project.demo.models.Product;

public class ProductCardController {

    @FXML
    private ImageView productImage;

    @FXML
    private Label productName;

    @FXML
    private Label productPrice;

    @FXML
    private Button addToCartButton;

    private Product product;

    public void setProduct(Product product) {
        this.product = product;

        // Set product details
        productName.setText(product.getName() != null ? product.getName() : "Unknown Product");
        productPrice.setText(product.getPrice() != null ? product.getPrice() : "Price: N/A");

        // Load product image
        try {
            if (product.getImagePath() != null) {
                Image image = new Image(getClass().getResourceAsStream(product.getImagePath()));
                productImage.setImage(image);
            } else {
                System.err.println("Image path is null for product: " + product.getName());
            }
        } catch (Exception e) {
            System.err.println("Failed to load image for product: " + product.getName());
        }

        // Set Add to Cart button action
        addToCartButton.setOnAction(event -> addToCart());
    }

    private void addToCart() {
        CartManager.getInstance().addProductToCart(product);
        System.out.println("Added to cart: " + product.getName());
    }

}
