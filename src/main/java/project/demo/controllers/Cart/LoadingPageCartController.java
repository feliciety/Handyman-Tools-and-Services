package project.demo.controllers.Cart;

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

public class LoadingPageCartController {

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
        map.put("loadingCart", "/project/demo/imagelogo/cartshop.gif");
        map.put("loadingDetails", "/project/demo/imagelogo/mapa.gif");
        map.put("loadingShipping", "/project/demo/imagelogo/truck.gif");
        map.put("loadingPayment", "/project/demo/imagelogo/payment.gif");
        map.put("loadingPaymentSuccess", "/project/demo/imagelogo/done.gif");
        // Add more mappings as needed
        return map;
    }

    /**
     * Initializes the loading page logic by setting the parent container, target FXML,
     * and dynamically selecting the GIF to display.
     *
     * @param parentContainer The parent container where the target FXML will replace the loading screen.
     * @param targetFxmlPath  Path to the target FXML file to be loaded after the loading animation.
     * @param gifKey          Key to select the appropriate GIF to display.
     */
    public void initializeLoading(AnchorPane parentContainer, String targetFxmlPath, String gifKey) {
        this.parentContainer = parentContainer;

        // Load GIF based on gifKey
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

        // Start fade-in animation and loading
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), gifLoader);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        fadeIn.setOnFinished(event -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(2)); // Loading duration
            pause.setOnFinished(e -> loadTargetView(targetFxmlPath)); // Load target FXML after fade-out
            pause.play();
        });

        fadeIn.play();
    }

    private void loadTargetView(String targetFxmlPath) {
        try {
            // Load the target FXML
            FXMLLoader targetLoader = new FXMLLoader(getClass().getResource(targetFxmlPath));
            Parent targetView = targetLoader.load();

            // Set the main controller reference if applicable
            Object controller = targetLoader.getController();
            if (controller instanceof CartTableController cartTableController) {
                cartTableController.setMainController(CartPageController.getInstance());
            } else if (controller instanceof DetailsController detailsController) {
                detailsController.setMainController(CartPageController.getInstance());
            } else if (controller instanceof ShippingController shippingController) {
                shippingController.setMainController(CartPageController.getInstance());
            } else if (controller instanceof PaymentController paymentController) {
                paymentController.setMainController(CartPageController.getInstance());
            } else if (controller instanceof PaymentSuccessController paymentSuccessController) {
                paymentSuccessController.setMainController(CartPageController.getInstance());
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