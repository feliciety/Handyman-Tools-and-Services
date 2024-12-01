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
    private Label usernameLabel;

    @FXML
    private Label useremailLabel;

    @FXML
    private AnchorPane contentPane;

    private final DatabaseConfig db = new DatabaseConfig();

    /**
     * Initialize the profile page, fetch data from the database, and update UI elements.
     */
    @FXML
    public void initialize() {
        updateUserDetails();
    }

    /**
     * Fetch user details from the database and update the UI.
     */
    private void updateUserDetails() {
        UserSession session = UserSession.getInstance();

        try (Connection connection = db.getConnection()) {
            String query = "SELECT username, email, profile_image_path FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, session.getUserId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String profileImagePath = resultSet.getString("profile_image_path");

                // Update labels
                usernameLabel.setText(username);
                useremailLabel.setText(email);

                // Update profile image
                if (profileImagePath != null && !profileImagePath.isEmpty()) {
                    File file = new File(profileImagePath);
                    if (file.exists()) {
                        profileImageCircle.setFill(new ImagePattern(new Image(file.toURI().toString())));
                    } else {
                        // Default image if the file path is invalid
                        profileImageCircle.setFill(new ImagePattern(new Image("default_profile_picture.png")));
                    }
                } else {
                    profileImageCircle.setFill(new ImagePattern(new Image("default_profile_picture.png")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to fetch user details: " + e.getMessage());
        }
    }

    /**
     * Handle changing the profile image.
     */
    @FXML
    public void handleImageChange(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(contentPane.getScene().getWindow());
        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();

            // Update the database with the new image path
            UserSession session = UserSession.getInstance();
            try (Connection connection = db.getConnection()) {
                String query = "UPDATE users SET profile_image_path = ? WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, imagePath);
                statement.setInt(2, session.getUserId());
                statement.executeUpdate();

                // Update the profile image in the UI
                profileImageCircle.setFill(new ImagePattern(new Image(selectedFile.toURI().toString())));

                System.out.println("[INFO] Profile image updated: " + imagePath);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("[ERROR] Failed to update profile image: " + e.getMessage());
            }
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
