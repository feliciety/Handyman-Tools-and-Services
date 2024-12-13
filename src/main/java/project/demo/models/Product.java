package project.demo.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Product {
    private final String name;
    private final double price; // Store price as a double
    private final String imagePath;
    private final String category;
    private int productId;

    public Product(String name, double price, String imagePath, String category) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
        this.category = category;
    }

    public Product(int productId, String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.imagePath = null; // Default to null if no image specified
        this.category = category;
        this.productId = productId; // Initialize productId field
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price; // Return raw double for calculations
    }

    public String getFormattedPrice() {
        return String.format("₱%.2f", price); // Format for display
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getCategory() {
        return category;
    }

    // ImageView getter for UI
    public ImageView getImageView() {
        try {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(75);
            imageView.setFitHeight(75);
            imageView.setPreserveRatio(true);
            return imageView;
        } catch (Exception e) {
            System.err.println("Error loading image: " + imagePath);
            return new ImageView();
        }
    }
}
