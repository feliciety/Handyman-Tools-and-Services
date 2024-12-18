package project.demo.controllers.Booking;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoadingPageBookingController {

    public AnchorPane loadingContainer;
    @FXML
    private ImageView gifLoader; // ImageView in LoadingPageCart.fxml

    private AnchorPane parentContainer; // The container where content is dynamically loaded
    private String targetFxmlPath; // Path to the target FXML
    private static final Map<String, String> gifMap = createGifMap(); // GIF mapping based on keys

    /**
     * Creates a mapping of gif keys to their respective GIF file paths.
     */
    private static Map<String, String> createGifMap() {
        Map<String, String> map = new HashMap<>();
        map.put("loadingBookingCartTable", "/project/demo/imagelogo/cartshop.gif");
        map.put("loadingAddressBookingDetails", "/project/demo/imagelogo/mapa.gif");
        map.put("loadingBookingPayment", "/project/demo/imagelogo/payment.gif");
        map.put("loadingBookPaymentSuccess", "/project/demo/imagelogo/done.gif");
        // Add more mappings as needed
        return map;
    }


    public void initializeLoading(AnchorPane parentContainer, String targetFxmlPath, String gifKey, BookingPageController mainController) {
        this.parentContainer = parentContainer;

        String gifPath = gifMap.get(gifKey);
        if (gifPath != null) {
            try {
                Image loadingGif = new Image(getClass().getResourceAsStream(gifPath));
                gifLoader.setImage(loadingGif);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("[ERROR] Failed to load GIF for key: " + gifKey);
            }
        } else {
            System.err.println("[ERROR] No matching GIF found for key: " + gifKey);
        }

        // Start fade-in animation
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), gifLoader);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        fadeIn.setOnFinished(event -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> loadTargetView(targetFxmlPath, mainController)); // Pass mainController
            pause.play();
        });

        fadeIn.play();
    }

    private void loadTargetView(String targetFxmlPath, BookingPageController mainController) {
        try {
            FXMLLoader targetLoader = new FXMLLoader(getClass().getResource(targetFxmlPath));
            Parent targetView = targetLoader.load();

            // Get the controller for the target FXML
            Object controller = targetLoader.getController();

            // Set the main controller reference
            if (controller instanceof AddressBookingDetailsController) {
                ((AddressBookingDetailsController) controller).setMainController(mainController);
            } else if (controller instanceof BookingCartTableController) {
                ((BookingCartTableController) controller).setMainController(mainController);
            } else if (controller instanceof BookingPaymentController) {
                ((BookingPaymentController) controller).setMainController(mainController);
            } else if (controller instanceof BookPaymentSuccessController) {
                ((BookPaymentSuccessController) controller).setMainController(mainController);
            }

            // Replace content in the parent container
            parentContainer.getChildren().setAll(targetView);

        } catch (IOException ex) {
            System.err.println("[ERROR] Failed to load target FXML: " + targetFxmlPath);
            ex.printStackTrace();
        }
    }

    /**
     * Handles the loading animation and subsequent navigation to the target page.
     */
    private void startLoadingAnimation() {
        // Pause for 3 seconds to display the loading animation
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            try {
                // Dynamically load the target FXML file
                FXMLLoader targetLoader = new FXMLLoader(getClass().getResource(targetFxmlPath));
                Parent targetView = targetLoader.load();

                // Replace the content of the parent container
                parentContainer.getChildren().setAll(targetView);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("[ERROR] Failed to load target FXML: " + targetFxmlPath);
            }
        });

        // Play the loading animation
        pause.play();
    }
}