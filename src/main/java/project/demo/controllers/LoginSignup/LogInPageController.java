package project.demo.controllers.LoginSignup;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.demo.DataBase.DatabaseConfig;
import project.demo.models.UserSession;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

public class LogInPageController {

    @FXML private TextField emailOrUsernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label warningLabel;
    @FXML private StackPane emailWarningImage;
    @FXML private StackPane passwordWarningImage;

    private final DatabaseConfig db = new DatabaseConfig();

    @FXML
    private void LogInButtonClicked() {
        String emailOrUsername = emailOrUsernameField.getText();
        String password = passwordField.getText();

        // Validation: Empty Fields
        if (emailOrUsername.isEmpty() && password.isEmpty()) {
            handleMultipleWarnings(
                    Arrays.asList(emailOrUsernameField, passwordField),
                    Arrays.asList(emailWarningImage, passwordWarningImage),
                    "Please fill in both fields."
            );
            return;
        }

        if (emailOrUsername.isEmpty()) {
            handleSingleWarning(emailOrUsernameField, emailWarningImage, "Please fill in email or username.");
            return;
        }

        if (password.isEmpty()) {
            handleSingleWarning(passwordField, passwordWarningImage, "Please fill in password.");
            return;
        }

        // Query the database
        String query = "SELECT * FROM users WHERE (email = ? OR username = ?) AND password = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, emailOrUsername);
            statement.setString(2, emailOrUsername);
            statement.setString(3, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Login Success! Welcome!");

                // Set user session
                UserSession session = UserSession.getInstance();
                session.setUserId(resultSet.getInt("id"));
                session.setUsername(resultSet.getString("username"));
                session.setEmail(resultSet.getString("email"));
                session.setContactNumber(resultSet.getString("contact_number"));

                // Load profile picture
                String profilePicturePath = resultSet.getString("profile_picture");
                if (profilePicturePath != null && !profilePicturePath.isEmpty()) {
                    File profileImageFile = new File(profilePicturePath);
                    if (profileImageFile.exists()) {
                        session.setUserImage(new Image(profileImageFile.toURI().toString()));
                        System.out.println("Profile picture loaded.");
                    } else {
                        loadDefaultImage(session);
                    }
                } else {
                    loadDefaultImage(session);
                }

                // Navigate to MainStructure
                navigateToPage("/project/demo/MainStructure.fxml", "Main Application");
            } else {
                handleIncorrectInputs(emailOrUsernameField, emailWarningImage, passwordField, passwordWarningImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            handleSingleWarning(emailOrUsernameField, emailWarningImage, "Database connection failed.");
        }
    }

    private void loadDefaultImage(UserSession session) {
        System.out.println("Profile picture not found. Loading default image.");
        session.setUserImage(new Image(getClass().getResourceAsStream("/project/demo/images/default_profile.png")));
    }

    private void handleSingleWarning(TextField field, StackPane warningImage, String message) {
        handleMultipleWarnings(
                List.of(field),
                List.of(warningImage),
                message
        );
    }

    private void handleMultipleWarnings(List<TextField> fields, List<StackPane> warningImages, String message) {
        warningLabel.setText(message);
        warningLabel.setTextFill(Color.RED);
        warningLabel.setVisible(true);

        fields.forEach(field -> field.setStyle("-fx-border-color: red; -fx-border-width: 2;"));
        warningImages.forEach(warningImage -> warningImage.setVisible(true));

        ParallelTransition parallelTransition = new ParallelTransition();
        for (TextField field : fields) {
            parallelTransition.getChildren().add(createShakeAnimation(field));
        }
        parallelTransition.getChildren().add(createFadeOutAnimation(warningLabel));

        parallelTransition.setOnFinished(event -> {
            fields.forEach(field -> field.setStyle("-fx-border-color: #67608f; -fx-border-radius: 5;"));
            warningImages.forEach(warningImage -> warningImage.setVisible(false));
            warningLabel.setVisible(false);
            warningLabel.setText("");
        });

        parallelTransition.play();
    }

    private void handleIncorrectInputs(TextField emailField, StackPane emailWarning, TextField passwordField, StackPane passwordWarning) {
        handleMultipleWarnings(
                Arrays.asList(emailField, passwordField),
                Arrays.asList(emailWarning, passwordWarning),
                "Incorrect email or password."
        );
    }

    private TranslateTransition createShakeAnimation(Node node) {
        TranslateTransition shake = new TranslateTransition(Duration.millis(100), node);
        shake.setByX(10);
        shake.setCycleCount(6);
        shake.setAutoReverse(true);
        return shake;
    }

    private FadeTransition createFadeOutAnimation(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(2), node);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        return fade;
    }

    @FXML
    public void SignInSwap() {
        navigateToPage("/project/demo/FXMLLoginSignup/SignUpPage.fxml", "Sign Up");
    }

    private void navigateToPage(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

            Stage currentStage = (Stage) emailOrUsernameField.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load the page.");
        }
    }
}
