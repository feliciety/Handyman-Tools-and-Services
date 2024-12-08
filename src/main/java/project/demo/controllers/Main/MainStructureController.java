package project.demo.controllers.Main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import project.demo.DataBase.DatabaseConfig;
import project.demo.models.UserSession;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainStructureController {

    @FXML
    private AnchorPane contentContainer;

    @FXML
    private Circle profileImageCircle;

    private String currentPage = "";

    private final DatabaseConfig db = new DatabaseConfig();

    @FXML
    public void initialize() {
        System.out.println("[DEBUG] MainStructureController initialized.");

        // Load the default home page
        loadPage("/project/demo/FXMLHomePage/HomePage.fxml");

        // Load and refresh the user's profile image
        loadProfileImageFromDatabase();
    }

    /**
     * Queries the user's profile picture path from the database
     * and initializes the profile image.
     */
    private void loadProfileImageFromDatabase() {
        String query = "SELECT profile_picture FROM users WHERE id = ?";
        int userId = UserSession.getInstance().getUserId();

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String imagePath = resultSet.getString("profile_picture");
                if (imagePath != null && !imagePath.isEmpty()) {
                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {
                        Image profileImage = new Image(imageFile.toURI().toString());
                        profileImageCircle.setFill(new ImagePattern(profileImage));
                        System.out.println("[INFO] Profile image loaded successfully from database.");
                    } else {
                        System.out.println("[WARNING] Profile image file not found. Using default image.");
                        setDefaultProfileImage();
                    }
                } else {
                    System.out.println("[WARNING] No profile image path in the database. Using default image.");
                    setDefaultProfileImage();
                }
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to load profile image from database.");
            e.printStackTrace();
            setDefaultProfileImage();
        }
    }

    /**
     * Sets a default profile image when none exists in the database.
     */
    private void setDefaultProfileImage() {
        Image defaultImage = new Image("/project/demo/images/default_profile.png");
        profileImageCircle.setFill(new ImagePattern(defaultImage));
    }

    /**
     * Loads the specified FXML page into the content container.
     *
     * @param fxmlPath Path to the FXML file.
     */
    private void loadPage(String fxmlPath) {
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

    public void handleAboutUsClick(javafx.event.ActionEvent actionEvent) {
        loadPage("/project/demo/FXMLAboutUsPage/AboutUsPage.fxml");
    }

    public void handleHomeClick(javafx.event.ActionEvent actionEvent) {
        loadPage("/project/demo/FXMLHomePage/HomePage.fxml");
    }

    public void handleShopClick(javafx.event.ActionEvent actionEvent) {
        loadPage("/project/demo/FXMLShopPage/ShopPage.fxml");
    }

    public void handleCartClick(javafx.event.ActionEvent actionEvent) {
        loadPage("/project/demo/FXMLCartPage/CartPage.fxml");
    }

    public void handleServiceClick(javafx.event.ActionEvent actionEvent) {
        loadPage("/project/demo/FXMLServicePage/ServicePage.fxml");
    }

    public void handleBookingClick(javafx.event.ActionEvent actionEvent) {
        loadPage("/project/demo/FXMLBookingPage/BookingPage.fxml");
    }
    public void handleEmployeeClick(javafx.event.ActionEvent actionEvent) {
        loadPage("/project/demo/FXMLBookingPage/BookingPage.fxml");
    }
}
