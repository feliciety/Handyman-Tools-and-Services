package project.demo.controllers.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import project.demo.models.CartManager;
import project.demo.models.UserSession;

import java.io.IOException;

public class MainStructureController {

    @FXML
    private AnchorPane contentContainer;

    @FXML
    private Circle profileImageCircle;

    private String currentPage = "";

    @FXML
    private Label cartCounterLabel;

    private static MainStructureController instance; // Singleton instance

    public MainStructureController() {
        instance = this;
    }

    public static MainStructureController getInstance() {
        return instance;
    }

    @FXML
    public void initialize() {

        updateCartCounter();
        System.out.println("[DEBUG] MainStructureController initialized.");

        // Load the default home page
        loadPage("/project/demo/FXMLHomePage/HomePage.fxml");

        // Dynamically update the profile image
        refreshProfileImage();
    }

    public void refreshProfileImage() {
        Image userProfileImage = UserSession.getInstance().getUserImage();
        profileImageCircle.setFill(new ImagePattern(userProfileImage));
        System.out.println("[INFO] MainStructureController profile image refreshed.");
    }



    /**
     * Loads the specified FXML page into the content container.
     *
     * @param fxmlPath Path to the FXML file.
     */
    public void loadPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane newPage = loader.load();

            // Retrieve the controller and inject MainStructureController
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



    public interface InjectMainController {
        void setMainStructureController(MainStructureController mainController);
    }

    public void handleProfileClick(MouseEvent mouseEvent) {
        System.out.println("[DEBUG] Profile Clicked - Event Triggered");
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
    private void updateCartCounter() {
        CartManager.getInstance().addCartCounterListener((observable, oldValue, newValue) -> {
            cartCounterLabel.setText(String.valueOf(newValue)); // Update label with the cart size
        });
    }


}
