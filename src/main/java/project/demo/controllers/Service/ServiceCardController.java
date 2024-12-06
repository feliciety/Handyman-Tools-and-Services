package project.demo.controllers.Service;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private Service service;

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
        BookServiceItem item = new BookServiceItem(
                service.getName(),
                "medium", // Default complexity
                String.valueOf(service.getPriceForComplexity("medium")), // Default price for medium complexity
                "N/A", // Default booking date
                service.getImagePath()
        );

        BookServiceManager.getInstance().addService(item);
        System.out.println("[DEBUG] Service added to cart: " + item.getServiceName());
    }
}
