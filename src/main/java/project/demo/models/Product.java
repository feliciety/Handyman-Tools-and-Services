package project.demo.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Product {

    private final String name;
    private final String price;
    private final String imagePath;
    private final String category;

    public Product(String name, String price, String imagePath, String category) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getCategory() {
        return category;
    }

    /**
     * Returns an ImageView created from the imagePath.
     */
    public ImageView getImageView() {
        try {
            // Ensure the image path is correct relative to the resources directory
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(image);

            // Set the desired dimensions for the image
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.setPreserveRatio(true);

            return imageView;
        } catch (Exception e) {
            System.err.println("Error loading image from path: " + imagePath);
            return new ImageView(); // Return an empty ImageView in case of an error
        }
    }
}
