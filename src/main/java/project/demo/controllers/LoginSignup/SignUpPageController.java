package project.demo.controllers.LoginSignup;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.demo.DataBase.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class SignUpPageController {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;

    @FXML private CheckBox termsNConditionCheckbox;

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

        // Validate Terms and Conditions checkbox
        if (!termsNConditionCheckbox.isSelected()) {
            showTermsWarning("You must agree to the Terms and Conditions.");
            valid = false;
        } else {
            clearTermsCheckboxWarning();
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

    private void handleSingleWarning(TextField field, StackPane warningImage, String message) {
        warningLabel.setText(message);
        warningLabel.setTextFill(Color.RED);
        warningLabel.setVisible(true);

        field.setStyle("-fx-border-color: red; -fx-border-width: 2;");
        warningImage.setVisible(true);
    }

    private void showTermsWarning(String message) {
        warningLabel.setText(message);
        warningLabel.setTextFill(Color.RED);
        warningLabel.setVisible(true);

        // Fade out the warning label
        FadeTransition fade = new FadeTransition(Duration.seconds(2), warningLabel);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(event -> warningLabel.setVisible(false));
        fade.play();
    }

    private void clearTermsCheckboxWarning() {
        termsNConditionCheckbox.setStyle("-fx-border-color: transparent;");
    }

    private void clearWarnings() {
        warningLabel.setVisible(false);
        warningLabel.setText("");
        fullNameWarningImage.setVisible(false);
        emailWarningImage.setVisible(false);
        passwordWarningImage.setVisible(false);
        confirmWarningImage.setVisible(false);

        usernameField.setStyle("-fx-border-color: #67608f;");
        emailField.setStyle("-fx-border-color: #67608f;");
        passwordField.setStyle("-fx-border-color: #67608f;");
        confirmPasswordField.setStyle("-fx-border-color: #67608f;");
        clearTermsCheckboxWarning();
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
