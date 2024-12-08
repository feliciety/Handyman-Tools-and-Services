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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.demo.DataBase.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

public class SignUpPageController {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;

    @FXML private Label warningLabel;

    @FXML private StackPane fullNameWarningImage;
    @FXML private StackPane emailWarningImage;
    @FXML private StackPane passwordWarningImage;
    @FXML private StackPane confirmWarningImage;

    private final DatabaseConfig db = new DatabaseConfig();

    @FXML
    private void signUpButtonClicked() {
        clearWarnings();

        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        boolean valid = true;

        if (username.isEmpty() && email.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
            handleAllFieldsEmptyWarning("All fields are required.");
            return;
        }

        // Validate Fields
        if (username.isEmpty()) {
            handleSingleWarning(usernameField, fullNameWarningImage, "Full name is required.");
            valid = false;
        }

        if (email.isEmpty()) {
            handleSingleWarning(emailField, emailWarningImage, "Email is required.");
            valid = false;
        } else if (!isValidEmail(email)) {
            handleSingleWarning(emailField, emailWarningImage, "Invalid email format.");
            valid = false;
        }

        if (password.isEmpty()) {
            handleSingleWarning(passwordField, passwordWarningImage, "Password is required.");
            valid = false;
        }

        if (confirmPassword.isEmpty()) {
            handleSingleWarning(confirmPasswordField, confirmWarningImage, "Please confirm your password.");
            valid = false;
        } else if (!password.equals(confirmPassword)) {
            handleSingleWarning(confirmPasswordField, confirmWarningImage, "Passwords do not match.");
            valid = false;
        }

        if (!valid) return;

        // Insert Data into Database
        String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);

            statement.executeUpdate();
            showSuccess("Account created successfully!");
            navigateToPage("/project/demo/FXMLLoginSignup/LogInPage.fxml", "Login");
        } catch (Exception e) {
            e.printStackTrace();
            handleSingleWarning(emailField, emailWarningImage, "Database connection failed.");
        }
    }
    private void handleAllFieldsEmptyWarning(String message) {
        // Set the warning message
        warningLabel.setText(message);
        warningLabel.setTextFill(Color.RED);
        warningLabel.setVisible(true);

        // Apply red borders to all text fields
        usernameField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
        emailField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
        passwordField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
        confirmPasswordField.setStyle("-fx-border-color: red; -fx-border-width: 2;");

        // Make all warning images visible
        fullNameWarningImage.setVisible(true);
        emailWarningImage.setVisible(true);
        passwordWarningImage.setVisible(true);
        confirmWarningImage.setVisible(true);

        // Create a parallel transition for simultaneous animations
        ParallelTransition parallelTransition = new ParallelTransition();

        // Add shake animations for text fields and warning images
        parallelTransition.getChildren().addAll(
                createShakeAnimation(usernameField), createShakeAnimation(fullNameWarningImage),
                createShakeAnimation(emailField), createShakeAnimation(emailWarningImage),
                createShakeAnimation(passwordField), createShakeAnimation(passwordWarningImage),
                createShakeAnimation(confirmPasswordField), createShakeAnimation(confirmWarningImage)
        );

        // Add fade-out animations for warning images
        parallelTransition.getChildren().addAll(
                createFadeOutAnimation(fullNameWarningImage),
                createFadeOutAnimation(emailWarningImage),
                createFadeOutAnimation(passwordWarningImage),
                createFadeOutAnimation(confirmWarningImage)
        );

        // Add fade-out animation for the warning label
        parallelTransition.getChildren().add(createFadeOutAnimation(warningLabel));

        // Reset the warning elements and text fields after the animation
        parallelTransition.setOnFinished(event -> {
            usernameField.setStyle("-fx-border-color: #67608f;");
            emailField.setStyle("-fx-border-color: #67608f;");
            passwordField.setStyle("-fx-border-color: #67608f;");
            confirmPasswordField.setStyle("-fx-border-color: #67608f;");

            fullNameWarningImage.setVisible(false);
            emailWarningImage.setVisible(false);
            passwordWarningImage.setVisible(false);
            confirmWarningImage.setVisible(false);

            warningLabel.setVisible(false);
        });

        // Play the animations
        parallelTransition.play();
    }


    private void handleSingleWarning(TextField field, StackPane warningImage, String message) {
        handleMultipleWarnings(
                List.of(field),
                List.of(warningImage),
                message
        );
    }

    private void handleMultipleWarnings(List<TextField> fields, List<StackPane> warningImages, String message) {
        // Set the warning message
        warningLabel.setText(message);
        warningLabel.setTextFill(Color.RED);
        warningLabel.setVisible(true);

        // Apply red borders and make warning images visible
        fields.forEach(field -> field.setStyle("-fx-border-color: red; -fx-border-width: 2;"));
        warningImages.forEach(warningImage -> warningImage.setVisible(true));

        ParallelTransition parallelTransition = new ParallelTransition();

        for (int i = 0; i < fields.size(); i++) {
            parallelTransition.getChildren().addAll(
                    createShakeAnimation(fields.get(i)),
                    createShakeAnimation(warningImages.get(i))
            );
        }

        parallelTransition.getChildren().add(createFadeOutAnimation(warningLabel));

        parallelTransition.setOnFinished(event -> clearWarnings());
        parallelTransition.play();
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

    private void clearWarnings() {
        warningLabel.setVisible(false);
        fullNameWarningImage.setVisible(false);
        emailWarningImage.setVisible(false);
        passwordWarningImage.setVisible(false);
        confirmWarningImage.setVisible(false);
        usernameField.setStyle("-fx-border-color: #67608f;");
        emailField.setStyle("-fx-border-color: #67608f;");
        passwordField.setStyle("-fx-border-color: #67608f;");
        confirmPasswordField.setStyle("-fx-border-color: #67608f;");
    }

    private void showSuccess(String message) {
        warningLabel.setText(message);
        warningLabel.setTextFill(Color.GREEN);
        warningLabel.setVisible(true);
    }

    @FXML
    public void LogInSwap() {
        navigateToPage("/project/demo/FXMLLoginSignup/LogInPage.fxml", "Login");
    }

    private void navigateToPage(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            handleSingleWarning(usernameField, fullNameWarningImage, "Failed to load the page.");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
}
