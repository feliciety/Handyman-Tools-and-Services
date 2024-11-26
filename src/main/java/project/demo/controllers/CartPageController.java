package project.demo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class CartPageController {

    @FXML
    private AnchorPane contentPane; // Main content area

    @FXML
    public void initialize() {
        System.out.println("Initializing CartPageController...");
        loadView("/project/demo/CartTable.fxml"); // Load CartTable as the default view
    }

    public void loadView(String fxmlPath) {
        try {
            System.out.println("Attempting to load view: " + fxmlPath);
            URL fxmlUrl = getClass().getResource(fxmlPath);

            if (fxmlUrl == null) {
                throw new IOException("FXML file not found: " + fxmlPath);
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            AnchorPane view = loader.load();

            // Pass the main controller to the child controller
            Object controller = loader.getController();
            if (controller instanceof CartTableController) {
                ((CartTableController) controller).setMainController(this);
            } else if (controller instanceof DetailsController) {
                ((DetailsController) controller).setMainController(this);
            } else if (controller instanceof ShippingController) {
                ((ShippingController) controller).setMainController(this);
            } else if (controller instanceof PaymentController) {
                ((PaymentController) controller).setMainController(this);
            }

            contentPane.getChildren().clear();
            contentPane.getChildren().add(view);

            // Ensure the new pane fills the entire AnchorPane
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

            System.out.println("FXML file loaded successfully: " + fxmlPath);
        } catch (IOException e) {
            System.err.println("Failed to load FXML file: " + fxmlPath);
            e.printStackTrace();
        }
    }
}
