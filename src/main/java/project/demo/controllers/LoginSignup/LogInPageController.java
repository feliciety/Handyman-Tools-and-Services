package project.demo.controllers.LoginSignup;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.demo.DataBase.DatabaseConfig;
import project.demo.models.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LogInPageController {

    @FXML
    private TextField emailOrUsernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label warningLabel;

    @FXML
    private StackPane emailWarningImage;

    @FXML
    private StackPane passwordWarningImage;

    private final DatabaseConfig db = new DatabaseConfig();

    @FXML
    private void LogInButtonClicked() {
        String emailOrUsername = emailOrUsernameField.getText();
        String password = passwordField.getText();

        // Check if both fields are empty
        if (emailOrUsername.isEmpty() && password.isEmpty()) {
            showSpecificWarning(emailOrUsernameField, emailWarningImage, "Please fill in email.");
            showSpecificWarning(passwordField, passwordWarningImage, "Please fill in password.");
            return;
        }

        // Check if only email field is empty
        if (emailOrUsername.isEmpty()) {
            showSpecificWarning(emailOrUsernameField, emailWarningImage, "Please fill in email.");
            return;
        }

        // Check if only password field is empty
        if (password.isEmpty()) {
            showSpecificWarning(passwordField, passwordWarningImage, "Please fill in password.");
            return;
        }

        // Validate email format
        if (!isValidEmail(emailOrUsername)) {
            showSpecificWarning(emailOrUsernameField, emailWarningImage, "Invalid email format.");
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

                // Populate UserSession
                UserSession session = UserSession.getInstance();
                session.setUserId(resultSet.getInt("id"));
                session.setUsername(resultSet.getString("username"));
                session.setEmail(resultSet.getString("email"));
                session.setContactNumber(resultSet.getString("contact_number"));

                System.out.println("User session created for: " + session.getUsername());

                // Navigate to the main application
                navigateToPage("/project/demo/MainStructure.fxml", "Main Application");
            } else {
                // Invalid email or password
                showSpecificWarning(emailOrUsernameField, emailWarningImage, "Incorrect email or username.");
                showSpecificWarning(passwordField, passwordWarningImage, "Incorrect password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showSpecificWarning(emailOrUsernameField, emailWarningImage, "Database connection failed.");
        }
    }

    private void showSpecificWarning(TextField field, StackPane warningImage, String message) {
        // Highlight the specific field in red
        field.setStyle("-fx-border-color: red; -fx-border-width: 2;");

        // Show the warning image
        warningImage.setVisible(true);

        // Display the error message in the warning label
        warningLabel.setText(message);
        warningLabel.setTextFill(Color.RED);

        // Add fade-in and fade-out effect for the warning label
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), warningLabel);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);

        // Add shake animation to the field
        TranslateTransition shake = new TranslateTransition(Duration.millis(100), field);
        shake.setByX(10);
        shake.setCycleCount(10); // Shake 10 times
        shake.setAutoReverse(true);

        // Combine shake and fade transitions
        SequentialTransition sequentialTransition = new SequentialTransition(shake, fadeTransition);
        sequentialTransition.play();

        // Reset styles and hide warning image after transitions
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            field.setStyle("-fx-border-color: #67608f; -fx-border-radius: 5;");
            warningImage.setVisible(false);
            warningLabel.setText("");
        });
        pause.play();
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

            // Close current window
            Stage currentStage = (Stage) emailOrUsernameField.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load the page.");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    private void loginUser(int userId, String username, String email, String contactNumber, String profilePicturePath) {
        UserSession session = UserSession.getInstance();
        session.setUserId(userId);
        session.setUsername(username);
        session.setEmail(email);
        session.setContactNumber(contactNumber);

        System.out.println("[INFO] User session initialized:");
        System.out.println("User ID: " + userId);
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Contact Number: " + contactNumber);
        System.out.println("Profile Picture Path: " + profilePicturePath);
    }
}
