package project.demo.controllers.Profile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import project.demo.DataBase.DatabaseConfig;
import project.demo.models.UserSession;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfilePageController {

    @FXML
    private Circle profileImageCircle;

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private AnchorPane contentPane;

    private final DatabaseConfig db = new DatabaseConfig();

    /**
     * Initialize the profile page, fetch data from the database, and update UI elements.
     */
    @FXML
    public void initialize() {

        loadView("/project/demo/FXMLProfilePage/EditProfile.fxml");
        setProfileImageFromDatabase();
        updateUserDetails();
    }

    /**
     * Fetch user details from the database and update the UI.
     */
    private void updateUserDetails() {
        try (Connection connection = db.getConnection()) {
            String query = "SELECT profile_picture FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, UserSession.getInstance().getUserId());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String imagePath = resultSet.getString("profile_picture");
                if (imagePath != null && !imagePath.isEmpty()) {
                    profileImageCircle.setFill(new ImagePattern(new javafx.scene.image.Image(imagePath)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * Sets the profile image in the circle from the database.
     */
    private void setProfileImageFromDatabase() {
        UserSession session = UserSession.getInstance();

        try (Connection connection = db.getConnection()) {
            String query = "SELECT profile_picture FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, session.getUserId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String imagePath = resultSet.getString("profile_picture");

                if (imagePath != null && !imagePath.isEmpty()) {
                    profileImageCircle.setFill(new ImagePattern(new javafx.scene.image.Image(imagePath)));
                } else {
                    // Set a default profile image if no image is found
                    profileImageCircle.setFill(new ImagePattern(new javafx.scene.image.Image(
                            getClass().getResource("/project/demo/imagelogo/default.png").toString())));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to load profile image: " + e.getMessage());
        }
    }

    /**
     * Handles changing the profile image.
     */
    @FXML
    public void handleImageChange() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(profileImageCircle.getScene().getWindow());
        if (selectedFile != null) {
            String newImagePath = selectedFile.toURI().toString(); // Convert to URI for JavaFX Image compatibility

            // Update the Circle's fill in real time
            profileImageCircle.setFill(new ImagePattern(new javafx.scene.image.Image(newImagePath)));

            // Save the new profile image path to the database
            updateProfileImageInDatabase(newImagePath);
        }
    }

    /**
     * Updates the profile image path in the database.
     *
     * @param imagePath The new image path.
     */
    private void updateProfileImageInDatabase(String imagePath) {
        UserSession session = UserSession.getInstance();

        try (Connection connection = db.getConnection()) {
            String query = "UPDATE users SET profile_picture = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, imagePath);
            statement.setInt(2, session.getUserId());
            statement.executeUpdate();

            System.out.println("[INFO] Profile picture updated in the database.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to update profile picture: " + e.getMessage());
        }
    }


    /**
     * Generic method to load views into the content pane.
     *
     * @param fxmlFilePath Path to the FXML file.
     */
    private void loadView(String fxmlFilePath) {
        try {
            AnchorPane newView = FXMLLoader.load(getClass().getResource(fxmlFilePath));
            contentPane.getChildren().clear();
            contentPane.getChildren().add(newView);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to load view: " + fxmlFilePath);
        }
    }

    @FXML
    public void clickEditProfile(ActionEvent actionEvent) {
        loadView("/project/demo/FXMLProfilePage/EditProfile.fxml");
    }

    @FXML
    public void clickOrderHistory(ActionEvent actionEvent) {
        loadView("/project/demo/FXMLProfilePage/OrderHistory.fxml");
    }

    @FXML
    public void clickServiceHistory(ActionEvent actionEvent) {
        loadView("/project/demo/FXMLProfilePage/ServiceHistory.fxml");
    }

    @FXML
    public void clickManageAddresses(ActionEvent actionEvent) {
        loadView("/project/demo/FXMLProfilePage/ManageAddresses.fxml");
    }

    @FXML
    public void clickPaymentMethods(ActionEvent actionEvent) {
        loadView("/project/demo/FXMLProfilePage/PaymentMethods.fxml");
    }
}
