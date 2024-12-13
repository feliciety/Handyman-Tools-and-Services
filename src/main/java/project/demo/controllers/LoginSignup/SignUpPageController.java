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
import java.util.Arrays;
import java.util.List;

public class SignUpPageController {

    public Button signUpButton;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private CheckBox termsCheckBox;
    @FXML private Label warningLabel;

    @FXML private StackPane fullNameWarningImage;
    @FXML private StackPane emailWarningImage;
    @FXML private StackPane passwordWarningImage;
    @FXML private StackPane confirmWarningImage;

    private MainPaneController mainPaneController;


    private final DatabaseConfig db = new DatabaseConfig();

    @FXML
    private void signUpButtonClicked() {
        clearWarnings();

        // Collect Input
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate Input
        if (validateFields(username, email, password, confirmPassword)) {
            insertUserData(username, email, password);
        }
    }

    // Input validation logic
    private boolean validateFields(String username, String email, String password, String confirmPassword) {
        boolean valid = true;

        if (username.isEmpty() && email.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
            showWarning("All fields are required.");
            handleAllFieldsEmpty();
            return false;
        }

        if (username.isEmpty()) {
            handleFieldWarning(usernameField, fullNameWarningImage, "Full name is required.");
            valid = false;
        }

        if (email.isEmpty()) {
            handleFieldWarning(emailField, emailWarningImage, "Email is required.");
            valid = false;
        } else if (!isValidEmail(email)) {
            handleFieldWarning(emailField, emailWarningImage, "Invalid email format.");
            valid = false;
        }

        if (password.isEmpty()) {
            handleFieldWarning(passwordField, passwordWarningImage, "Password is required.");
            valid = false;
        }

        if (confirmPassword.isEmpty()) {
            handleFieldWarning(confirmPasswordField, confirmWarningImage, "Please confirm your password.");
            valid = false;
        } else if (!password.equals(confirmPassword)) {
            handleFieldWarning(confirmPasswordField, confirmWarningImage, "Passwords do not match.");
            valid = false;
        }

        if (!termsCheckBox.isSelected()) {
            showWarning("You must agree to the Terms and Conditions.");
            valid = false;
        }

        return valid;
    }

    private void insertUserData(String username, String email, String password) {
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
            showWarning("Database connection failed.");
        }
    }

    private void handleAllFieldsEmpty() {
        Arrays.asList(usernameField, emailField, passwordField, confirmPasswordField)
                .forEach(field -> field.setStyle("-fx-border-color: red; -fx-border-width: 2;"));

        showFieldAnimations(Arrays.asList(fullNameWarningImage, emailWarningImage, passwordWarningImage, confirmWarningImage));
    }

    private void handleFieldWarning(Node field, StackPane warningImage, String message) {
        showWarning(message);
        if (field instanceof TextField) {
            ((TextField) field).setStyle("-fx-border-color: red; -fx-border-width: 2;");
        }
        if (warningImage != null) {
            warningImage.setVisible(true);
            createShakeAnimation(warningImage).play();
        }
    }

    private void showWarning(String message) {
        warningLabel.setText(message);
        warningLabel.setTextFill(Color.RED);
        warningLabel.setVisible(true);
        createFadeOutAnimation(warningLabel).play();
    }

    private void showSuccess(String message) {
        warningLabel.setText(message);
        warningLabel.setTextFill(Color.GREEN);
        warningLabel.setVisible(true);
    }

    private void showFieldAnimations(List<StackPane> warningImages) {
        warningImages.forEach(image -> {
            image.setVisible(true);
            createShakeAnimation(image).play();
            createFadeOutAnimation(image).play();
        });
    }

    private TranslateTransition createShakeAnimation(Node node) {
        TranslateTransition shake = new TranslateTransition(Duration.millis(100), node);
        shake.setByX(10);
        shake.setCycleCount(6);
        shake.setAutoReverse(true);
        return shake;
    }

    private FadeTransition createFadeOutAnimation(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(4), node);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        return fade;
    }

    private void clearWarnings() {
        warningLabel.setVisible(false);
        Arrays.asList(fullNameWarningImage, emailWarningImage, passwordWarningImage, confirmWarningImage)
                .forEach(image -> image.setVisible(false));

        Arrays.asList(usernameField, emailField, passwordField, confirmPasswordField)
                .forEach(field -> field.setStyle(""));
    }

    public void setMainPaneController(MainPaneController mainPaneController) {
        this.mainPaneController = mainPaneController;
    }
    @FXML
    public void initialize() {
        signUpButton.setOnAction(event -> {
            // Load LogInPage with loading screen
            mainPaneController.loadLogInPage();
        });
    }


    @FXML
    public void LogInSwap() {
        if (mainPaneController != null) {
            mainPaneController.loadLogInPage();
        }
    }

    private void navigateToPage(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            showWarning("Failed to load the page.");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
}
