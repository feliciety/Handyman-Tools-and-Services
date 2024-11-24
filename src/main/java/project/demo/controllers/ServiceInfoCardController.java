    package project.demo.controllers;

    import javafx.fxml.FXML;
    import javafx.scene.control.Label;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import project.demo.models.Service;

    public class ServiceInfoCardController {

        @FXML
        private ImageView serviceImage;

        @FXML
        private Label serviceTitle;

        @FXML
        private Label serviceDescription;

        public void setServiceInfo(Service service) {
            serviceTitle.setText(service.getName());
            serviceDescription.setText(service.getDescription());
            try {
                serviceImage.setImage(new Image(getClass().getResourceAsStream(service.getImagePath())));
            } catch (Exception e) {
                System.out.println("Image not found: " + service.getImagePath());
            }
        }
    }
