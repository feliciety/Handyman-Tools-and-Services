package project.demo.controllers.Service;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.demo.models.Service;

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
        servicePrice.setText(homeService.getPrice());

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

        bookNowButton.setOnAction(event -> System.out.println("Booking homeService: " + homeService.getName()));
    }
}
