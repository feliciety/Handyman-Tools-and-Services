package project.demo.controllers.Shop;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
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

    @FXML
    private Label buttonText; // Button text for animation

    @FXML
    private ImageView cartIcon; // Cart GIF ImageView

    @FXML
    private StackPane cartPane;

    private Product product;
    private boolean isGifLoaded = false;

    public void initialize() {
        System.out.println("[DEBUG] ProductCardController initialized.");

        // Ensure cart icon is hidden initially
        cartIcon.setOpacity(0);
        cartIcon.setVisible(false);

        addToCartButton.setOnAction(event -> playAddToCartAnimation());
    }

    public void setProduct(Product product) {
        this.product = product;

        // Set product details
        productName.setText(product.getName());
        productPrice.setText(product.getFormattedPrice());

        // Load product image
        try {
            Image image = new Image(getClass().getResourceAsStream(product.getImagePath()));
            productImage.setImage(image);
        } catch (Exception e) {
            System.err.println("Failed to load image for product: " + product.getName());
        }

        addToCartButton.setOnAction(event -> addToCart());
    }

    private void addToCart() {
        CartManager.getInstance().addProductToCart(product);
        System.out.println("Added to cart: " + product.getName());
        playAddToCartAnimation();
    }

    private void playAddToCartAnimation() {
        System.out.println("[INFO] Add to Cart clicked.");

        // Step 1: Animate button text up and fade out
        animateButtonTextUp();

        // Step 2: Show and animate the cart GIF
        loadAndAnimateCartGif();

        // Step 3: Reset the button text after cart animation
        PauseTransition resetDelay = new PauseTransition(Duration.seconds(3));
        resetDelay.setOnFinished(event -> showAddedToCartText());
        resetDelay.play();
    }

    private void animateButtonTextUp() {
        System.out.println("[DEBUG] Animating button text up.");

        TranslateTransition textUp = new TranslateTransition(Duration.seconds(0.5), buttonText);
        textUp.setByY(-20);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), buttonText);
        fadeOut.setToValue(0);

        ParallelTransition upAndFadeOut = new ParallelTransition(textUp, fadeOut);
        upAndFadeOut.play();
    }

    private void loadAndAnimateCartGif() {
        System.out.println("[DEBUG] Loading and animating cart GIF.");

        if (!isGifLoaded) {
            try {
                String gifPath = "/project/demo/imagelogo/cart.gif";
                Image gif = new Image(getClass().getResourceAsStream(gifPath));
                cartIcon.setImage(gif);
                isGifLoaded = true;
                System.out.println("[INFO] Cart GIF loaded successfully.");
            } catch (Exception e) {
                System.err.println("[ERROR] Failed to load cart GIF: " + e.getMessage());
                return;
            }
        }

        // Show the cart GIF initially
        cartIcon.setVisible(true);
        cartIcon.setOpacity(0);

        // Step 1: Fade in animation
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), cartIcon);
        fadeIn.setToValue(1);

        // Step 2: Move the cart GIF from x = -30 to x = 30
        TranslateTransition moveCart = new TranslateTransition(Duration.seconds(2), cartIcon);

        // Step 3: Fade out animation
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), cartIcon);
        fadeOut.setToValue(0);
        fadeOut.setDelay(Duration.seconds(0.5)); // Start fading out after 1.5 seconds

        // Step 4: Combine the animations
        ParallelTransition moveAndFadeIn = new ParallelTransition(fadeIn, moveCart);
        SequentialTransition cartAnimation = new SequentialTransition(moveAndFadeIn, fadeOut);

        cartAnimation.setOnFinished(event -> cartIcon.setVisible(false)); // Hide the GIF at the end
        cartAnimation.play();
    }


    private void showAddedToCartText() {
        System.out.println("[DEBUG] Showing 'Added to Cart' text.");

        // Step 1: Set "Added to Cart" text and make it fully visible
        buttonText.setText("Done!");
        buttonText.setOpacity(1); // Ensure it's visible

        // Step 2: Text down animation
        TranslateTransition textDown = new TranslateTransition(Duration.seconds(0.3), buttonText);
        textDown.setByY(20); // Move text down by 20 pixels

        // Step 3: Mini bounce effect (slight upward movement and settle)
        TranslateTransition bounceUp = new TranslateTransition(Duration.seconds(0.2), buttonText);
        bounceUp.setByY(-5); // Slight bounce up

        TranslateTransition settleDown = new TranslateTransition(Duration.seconds(0.2), buttonText);
        settleDown.setByY(5); // Settle back down

        // Step 3: Add a 1-second pause after the bounce
        PauseTransition pause = new PauseTransition(Duration.seconds(2));

        // Combine bounce animations sequentially
        SequentialTransition bounceEffect = new SequentialTransition(bounceUp, settleDown, pause);


        // Step 4: Fade out animation AFTER text down and bounce
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), buttonText);
        fadeOut.setToValue(0); // Fade out completely

        // Step 5: Combine text down, bounce, and fade out sequentially
        SequentialTransition textDownBounceAndFadeOut = new SequentialTransition(
                textDown, // Move down
                bounceEffect, // Mini bounce
                fadeOut // Fade out
        );

        // Step 6: Once "Added to Cart" animation finishes, reset to "+ Add to Cart"
        textDownBounceAndFadeOut.setOnFinished(event -> returnToOriginalText());

        // Play the combined animation
        textDownBounceAndFadeOut.play();
    }

    private void returnToOriginalText() {
        System.out.println("[DEBUG] Returning to original text.");

        // Step 1: Reset text to "+ Add to Cart" and position
        buttonText.setText("+ Add to Cart");
        buttonText.setTranslateY(0); // Reset position to original

        // Step 2: Fade In animation
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), buttonText);
        fadeIn.setToValue(1); // Fade back to fully visible

        // Play fade-in animation
        fadeIn.play();
    }

}
