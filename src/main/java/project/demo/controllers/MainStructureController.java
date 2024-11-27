package project.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainStructureController {

    @FXML
    private AnchorPane contentContainer; // The placeholder for dynamic content

    private String currentPage = ""; // Track the currently loaded page

    @FXML
    public void initialize() {
        // Load the initial page (HomePage.fxml) when the application starts
        loadPage("/project/demo/FXMLCartPage/CartPage.fxml");
    }

    /**
     * Utility method to load an FXML page into the contentContainer.
     *
     * @param fxmlFile the FXML file to load
     */
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

            // Clear current content and load the new page
            contentContainer.getChildren().clear();
            contentContainer.getChildren().add(newPage);

            currentPage = fxmlFile;
            System.out.println("[INFO] Successfully loaded: " + fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to load page: " + fxmlFile);
        }
    }

    // Handle Home button click
    public void handleHomeClick(ActionEvent actionEvent) {
        System.out.println("Cart Home Page button clicked");
        loadPage("/project/demo/HomePage.fxml");
    }

    // Handle Shop button click
    public void handleShopClick(ActionEvent actionEvent) {
        System.out.println("Shop Page Page button clicked");
        loadPage("/project/demo/ShopPage.fxml");
    }

    // Handle Cart button click
    public void handleCartClick(ActionEvent actionEvent) {
        System.out.println("Cart Page button clicked");
        loadPage("/project/demo/FXMLCartPage/CartPage.fxml");
    }

    public void handleServiceClick(ActionEvent actionEvent) {
        System.out.println("Service Page button clicked");
        loadPage("/project/demo/FXMLServicePage/ServicePage.fxml");
    }

    // Add this method to handle Booking button clicks
    public void handleBookingClick(ActionEvent actionEvent) {
        System.out.println("Booking Page button clicked");
        loadPage("/project/demo/BookingPage.fxml");
    }
}
