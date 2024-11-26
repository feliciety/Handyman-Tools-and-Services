
package project.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainStructureController {

    @FXML
    private AnchorPane contentContainer;

    private String currentPage = "";

    private void loadPage(String fxmlFile) {
        if (contentContainer == null) {
            System.err.println("[ERROR] contentContainer is null.");
            return;
        }

        if (currentPage.equals(fxmlFile)) {
            System.out.println("[INFO] Page already loaded: " + fxmlFile);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane newPage = loader.load();

            contentContainer.getChildren().clear();
            contentContainer.getChildren().add(newPage);

            Object controller = loader.getController();
            if (controller instanceof ShopPageController) {
                ((ShopPageController) controller).resetState();
            }

            currentPage = fxmlFile;
            System.out.println("[INFO] Successfully loaded: " + fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to load page: " + fxmlFile);
        }
    }

    public void handleHomeClick(ActionEvent actionEvent) {
        loadPage("/project/demo/HomePage.fxml");
    }

    public void handleShopClick(ActionEvent actionEvent) {
        loadPage("/project/demo/ShopPage.fxml");
    }

    public void handleCartClick(ActionEvent actionEvent) {
        loadPage("/project/demo/CartPage.fxml");
    }

    public void handleServiceClick(ActionEvent actionEvent) {
        loadPage("/project/demo/ServicePage.fxml");
    }

    public void handleBookingClick(ActionEvent actionEvent) {
        loadPage("/project/demo/BookingPage.fxml");
    }
}
