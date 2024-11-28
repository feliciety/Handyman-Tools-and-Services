package project.demo.controllers.Service;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.demo.models.Service;

public class ServiceCardController {

    @FXML
    private ImageView serviceImage;

    @FXML
    private Label serviceName;

    @FXML
    private Label serviceDescription;

    @FXML
    private Label servicePrice;

    @FXML
    private Button bookNowButton;

    public void setServiceDetails(Service homeService) {
        serviceName.setText(homeService.getName());
        serviceDescription.setText(homeService.getDescription());
        servicePrice.setText(homeService.getPrice());
        serviceImage.setImage(new Image(homeService.getImagePath())); // Set the homeService image
        bookNowButton.setOnAction(event -> System.out.println("Booking homeService: " + homeService.getName()));
    }
}
