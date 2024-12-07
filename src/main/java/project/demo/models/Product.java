package project.demo.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Product {
    private final String name;
    private final double price; // Store price as a double
    private final String imagePath;
    private final String category;

    public Product(String name, double price, String imagePath, String category) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
        this.category = category;
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
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.setPreserveRatio(true);
            return imageView;
        } catch (Exception e) {
            System.err.println("Error loading image: " + imagePath);
            return new ImageView();
        }
    }
}
