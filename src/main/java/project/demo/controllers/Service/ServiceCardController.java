package project.demo.controllers.Service;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import project.demo.models.Service;
import project.demo.models.BookServiceManager;
import project.demo.models.BookServiceItem;

public class ServiceCardController {

    @FXML
    private Label serviceName;

    @FXML
    private Label serviceDescription;

    @FXML
    private Label servicePrice;

    @FXML
    private ImageView serviceImage;

    @FXML
    private Button bookNowButton;

    @FXML
    private Label buttonText; // For button animation

    @FXML
    private ImageView successIcon; // Success GIF ImageView

    @FXML
    private StackPane successPane;

    private Service service;
    private boolean isGifLoaded = false;

    /**
     * Initialize the controller with default behaviors.
     */
    public void initialize() {
        System.out.println("[DEBUG] ServiceCardController initialized.");

        // Ensure success GIF is hidden initially
        successIcon.setOpacity(0);
        successIcon.setVisible(false);

        bookNowButton.setOnAction(event -> onBookNowClicked());
    }

    /**
     * Set the service details on the card.
     * @param service the service object
     */
    public void setServiceDetails(Service service) {
        this.service = service; // Store the current service
        serviceName.setText(service.getName());
        serviceDescription.setText(service.getDescription());
        servicePrice.setText(service.getFormattedPrice());

        // Load service image
        try {
            String imagePath = service.getImagePath();
            if (getClass().getResource(imagePath) != null) {
                serviceImage.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
            } else {
                System.err.println("[ERROR] Image not found: " + imagePath);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("[ERROR] Invalid image path: " + e.getMessage());
        }
    }

    /**
     * Handles the "Book Now" button click.
     */
    @FXML
    private void onBookNowClicked() {
        if (service == null) {
            System.err.println("[ERROR] Service is null when clicking Book Now.");
            return;
        }

        System.out.println("[DEBUG] Book Now clicked for service: " + service.getName());

        // Add the service to the cart
        BookServiceItem item = new BookServiceItem(service, "low", "");
        BookServiceManager.getInstance().addService(item);
        System.out.println("[DEBUG] Service added to cart: " + item.getServiceName());

        // Play the animation
        playBookNowAnimation();
    }

    /**
     * Plays the booking animation.
     */
    private void playBookNowAnimation() {
        System.out.println("[INFO] Book Now animation triggered.");

        // Step 1: Animate button text up and fade out
        animateButtonTextUp();

        // Step 2: Show and animate the success GIF
        loadAndAnimateSuccessGif();

        // Step 3: Reset the button text after the animation
        PauseTransition resetDelay = new PauseTransition(Duration.seconds(3));
        resetDelay.setOnFinished(event -> showBookToCartText());
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

    private void loadAndAnimateSuccessGif() {
        System.out.println("[DEBUG] Loading and animating success GIF.");

        if (!isGifLoaded) {
            try {
                String gifPath = "/project/demo/imagelogo/booking.gif";
                Image gif = new Image(getClass().getResourceAsStream(gifPath));
                successIcon.setImage(gif);
                isGifLoaded = true;
                System.out.println("[INFO] Success GIF loaded successfully.");
            } catch (Exception e) {
                System.err.println("[ERROR] Failed to load success GIF: " + e.getMessage());
                return;
            }
        }

        // Show the success GIF
        successIcon.setVisible(true);
        successIcon.setOpacity(0);

        // Step 1: Fade in animation
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), successIcon);
        fadeIn.setToValue(1);


        // Step 3: Fade out animation
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), successIcon);
        fadeOut.setToValue(0);
        fadeOut.setDelay(Duration.seconds(1.5));

        // Combine animations
        SequentialTransition animation = new SequentialTransition(fadeIn, fadeOut);
        animation.setOnFinished(event -> successIcon.setVisible(false)); // Hide the GIF at the end
        animation.play();
    }

    private void resetButtonText() {
        System.out.println("[DEBUG] Resetting button text.");

        buttonText.setText("Book Service");
        buttonText.setTranslateY(0);
        buttonText.setOpacity(1);
    }


    private void showBookToCartText() {
        System.out.println("[DEBUG] Showing 'Added to Cart' text.");

        // Step 1: Set "Added to Cart" text and make it fully visible
        buttonText.setText("Added to Cart!");
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
        buttonText.setText("Book Service");
        buttonText.setTranslateY(0); // Reset position to original

        // Step 2: Fade In animation
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), buttonText);
        fadeIn.setToValue(1); // Fade back to fully visible

        // Play fade-in animation
        fadeIn.play();
    }
}
