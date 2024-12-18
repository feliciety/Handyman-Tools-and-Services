package project.demo.controllers.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import project.demo.controllers.Cart.CartTableController;
import project.demo.models.CartManager;
import project.demo.models.UserSession;

import java.io.IOException;

public class MainStructureController {

    @FXML
    private AnchorPane contentContainer; // Consistent AnchorPane for dynamically loaded content

    @FXML
    private Circle profileImageCircle;

    private String currentPage = "";

    @FXML
    private Label cartCounterLabel;

    /**
     * Initializes the MainStructureController.
     */
    @FXML
    public void initialize() {
        updateCartCounter();
        System.out.println("[DEBUG] MainStructureController initialized.");
        loadPage("/project/demo/FXMLHomePage/HomePage.fxml");
        refreshProfileImage();
    }

    /**
     * Refreshes the profile image displayed in the Circle.
     */
    public void refreshProfileImage() {
        Image userProfileImage = UserSession.getInstance().getUserImage();
        profileImageCircle.setFill(new ImagePattern(userProfileImage));
        System.out.println("[INFO] Profile image refreshed.");
    }

    /**
     * Loads the specified FXML page into the content container.
     * @param fxmlPath Path to the FXML file.
     */
    private void loadPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane newPage = loader.load();

            // Inject MainStructureController into the loaded controller if applicable
            Object controller = loader.getController();
            if (controller instanceof InjectMainController) {
                ((InjectMainController) controller).setMainStructureController(this);
            }

            contentContainer.getChildren().clear();
            contentContainer.getChildren().add(newPage);

            currentPage = fxmlPath;
            System.out.println("[INFO] Successfully loaded: " + fxmlPath);
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to load page: " + fxmlPath);
            e.printStackTrace();
        }
    }

    /**
     * A specific method to load the CartTable.
     */
    @FXML
    private void loadCartTable() {
        System.out.println("[DEBUG] Attempting to load CartTable.fxml...");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLCartPage/CartTable.fxml"));
            Parent cartView = loader.load();

            // Retrieve and inject MainStructureController into CartTableController
            CartTableController cartController = loader.getController();
            if (cartController != null) {
                cartController.setMainStructureController(this);
                System.out.println("[INFO] MainStructureController injected into CartTableController.");
            } else {
                System.err.println("[ERROR] CartTableController is NULL.");
            }

            contentContainer.getChildren().clear();
            contentContainer.getChildren().add(cartView);

            System.out.println("[INFO] Successfully loaded CartTable.fxml.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to load CartTable.fxml.");
        }
    }


    /**
     * Updates the cart counter label in the UI whenever the cart size changes.
     */
    private void updateCartCounter() {
        CartManager.getInstance().addCartCounterListener((observable, oldValue, newValue) -> {
            cartCounterLabel.setText(String.valueOf(newValue));
        });
    }

    /* Event Handlers for Navigation and UI Buttons */
    public void handleProfileClick(MouseEvent mouseEvent) {
        System.out.println("[DEBUG] Profile Clicked.");
        loadPage("/project/demo/FXMLProfilePage/ProfilePage.fxml");
    }

    public void handleAboutUsClick(ActionEvent actionEvent) {
        System.out.println("[DEBUG] About Us button clicked.");
        loadPage("/project/demo/FXMLAboutUsPage/AboutUsPage.fxml");
    }

    public void handleHomeClick(ActionEvent actionEvent) {
        loadPage("/project/demo/FXMLHomePage/HomePage.fxml");
    }

    public void handleShopClick(ActionEvent actionEvent) {
        loadPage("/project/demo/FXMLShopPage/ShopPage.fxml");
    }

    public void handleCartClick(ActionEvent actionEvent) {
        loadPage("/project/demo/FXMLCartPage/CartPage.fxml");
    }

    public void handleServiceClick(ActionEvent actionEvent) {
        loadPage("/project/demo/FXMLServicePage/ServicePage.fxml");
    }

    public void handleBookingClick(ActionEvent actionEvent) {
        loadPage("/project/demo/FXMLBookingPage/BookingPage.fxml");
    }

    public void handleEmployeeClick(ActionEvent actionEvent) {
        loadPage("/project/demo/FXMLEmployeesPage/EmployeesPage.fxml");
    }

    /**
     * Dynamically loads the specified FXML page into the content container.
     *
     * @param fxmlPath The path to the FXML file to load.
     */
    public void navigateTo(String fxmlPath) {
        try {
            // Load the specified FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();

            // Check if the controller of the loaded file needs MainStructureController injected
            Object controller = loader.getController();
            if (controller instanceof InjectMainController) {
                ((InjectMainController) controller).setMainStructureController(this);
                System.out.println("[INFO] MainStructureController injected into: " + controller.getClass().getSimpleName());
            }

            // Replace the content in the content container
            contentContainer.getChildren().clear();
            contentContainer.getChildren().add(view);
            System.out.println("[INFO] Successfully navigated to: " + fxmlPath);
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to navigate to: " + fxmlPath);
            e.printStackTrace();
        }
    }

    /**
     * Interface to allow child controllers to receive a reference to MainStructureController.
     */
    public interface InjectMainController {
        void setMainStructureController(MainStructureController mainController);
    }
}