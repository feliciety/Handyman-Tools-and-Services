package project.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.demo.models.Service;

import java.io.InputStream;

public class ServiceCardController {

    @FXML
    private ImageView serviceImage;

    @FXML
    private Label serviceTypeLabel;

    @FXML
    private Label shortdescription;

    @FXML
    private Button viewDetails;

    private Service service;

    private OnViewDetailsListener onViewDetailsListener;

    public void setService(Service service, OnViewDetailsListener listener) {
        this.service = service;
        this.onViewDetailsListener = listener;

        serviceTypeLabel.setText(service.getName());
        shortdescription.setText(service.getShortDescription());

        // Load the image safely
        String imagePath = service.getImagePath();
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream == null) {
            System.err.println("Image not found: " + imagePath);
            imageStream = getClass().getResourceAsStream("/project/demo/imageservices/RoofingServices.png"); // Fallback image
        }
        serviceImage.setImage(new Image(imageStream));

        // Attach event handler
        viewDetails.setOnAction(event -> {
            if (onViewDetailsListener != null) {
                onViewDetailsListener.onViewDetails(service);
            }
        });
    }

    public void handleViewDetails(ActionEvent actionEvent) {
        if (onViewDetailsListener != null) {
            onViewDetailsListener.onViewDetails(service);
        }
    }

    // Listener interface for handling view details action
    public interface OnViewDetailsListener {
        void onViewDetails(Service service);
    }
}
