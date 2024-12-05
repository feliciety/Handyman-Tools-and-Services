package project.demo.controllers.Service;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.demo.models.Service;

import java.io.IOException;

public class ServiceCardController {
    @FXML
    private Label serviceName; // Corresponds to service_name
    @FXML
    private Label serviceDescription; // Corresponds to service_description
    @FXML
    private Label servicePrice; // Corresponds to service_price
    @FXML
    private ImageView serviceImage; // Corresponds to service_image_path

    @FXML
    private Button bookNowButton;

    public void setServiceDetails(Service homeService) {
        serviceName.setText(homeService.getName());
        serviceDescription.setText(homeService.getDescription());
        servicePrice.setText(homeService.getFormattedPrice());

        try {
            String imagePath = homeService.getImagePath();
            System.out.println("[DEBUG] Loading image from path: " + imagePath);

            if (getClass().getResource(imagePath) != null) {
                serviceImage.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
            } else {
                System.err.println("[ERROR] Image not found at path: " + imagePath);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("[ERROR] Invalid image path: " + e.getMessage());
        }

        // Set the action for the "Book Now" button
        bookNowButton.setOnAction(event -> {
            System.out.println("Booking service: " + homeService.getName());
            openBookSchedule();
        });
    }

    /**
     * Opens the BookSchedule.fxml file in a new pop-up window.
     */
    private void openBookSchedule() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLBookingPage/BookSchedule.fxml"));
            Scene scene = new Scene(loader.load());

            // Create a new Stage for the pop-up
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Schedule Booking");
            stage.initModality(Modality.APPLICATION_MODAL); // Makes the pop-up modal
            stage.setResizable(false); // Prevent resizing of the pop-up
            stage.showAndWait(); // Wait for the user to close the pop-up before returning to the main window

        } catch (IOException e) {
            System.err.println("[ERROR] Failed to load BookSchedule.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
