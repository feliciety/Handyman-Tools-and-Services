package project.demo.controllers.LoginSignup;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.demo.DataBase.DatabaseConfig;
import project.demo.models.UserSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

public class LogInPageController {

    public Button LogInButton;
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

    private MainPaneController mainPaneController;

    private final DatabaseConfig db = new DatabaseConfig();

    @FXML
    private void LogInButtonClicked() {
        String emailOrUsername = emailOrUsernameField.getText();
        String password = passwordField.getText();

        // Check if both fields are empty
        if (emailOrUsername.isEmpty() && password.isEmpty()) {
            handleMultipleWarnings(
                    Arrays.asList(emailOrUsernameField, passwordField),
                    Arrays.asList(emailWarningImage, passwordWarningImage),
                    "Please fill in both fields."
            );
            return;
        }

        // Check if only email field is empty
        if (emailOrUsername.isEmpty()) {
            handleSingleWarning(emailOrUsernameField, emailWarningImage, "Please fill in email.");
            return;
        }

        // Check if only password field is empty
        if (password.isEmpty()) {
            handleSingleWarning(passwordField, passwordWarningImage, "Please fill in password.");
            return;
        }

        // Validate email format
        if (!isValidEmail(emailOrUsername)) {
            handleSingleWarning(emailOrUsernameField, emailWarningImage, "Invalid email format.");
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
                handleIncorrectInputs(emailOrUsernameField, emailWarningImage, passwordField, passwordWarningImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            handleSingleWarning(emailOrUsernameField, emailWarningImage, "Database connection failed.");
        }
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

        // Apply red borders to all fields and make warning images visible
        fields.forEach(field -> field.setStyle("-fx-border-color: red; -fx-border-width: 2;"));
        warningImages.forEach(warningImage -> warningImage.setVisible(true));

        // Create animations for all fields and warning images
        ParallelTransition parallelTransition = new ParallelTransition();

        for (TextField field : fields) {
            parallelTransition.getChildren().add(createShakeAnimation(field));
            parallelTransition.getChildren().add(createBorderFadeAnimation(field));
        }

        for (StackPane warningImage : warningImages) {
            parallelTransition.getChildren().add(createShakeAnimation(warningImage));
            parallelTransition.getChildren().add(createFadeOutAnimation(warningImage));
        }

        // Add fade-out animation for the warning label
        parallelTransition.getChildren().add(createFadeOutAnimation(warningLabel));

        // Reset styles and visibility after animations
        parallelTransition.setOnFinished(event -> {
            fields.forEach(field -> field.setStyle("-fx-border-color: #67608f; -fx-border-radius: 5;"));
            warningImages.forEach(warningImage -> warningImage.setVisible(false));
            warningLabel.setVisible(false);
            warningLabel.setText("");
        });

        // Play animations
        parallelTransition.play();
    }

    private void handleIncorrectInputs(TextField emailField, StackPane emailWarning, TextField passwordField, StackPane passwordWarning) {
        String message = "Incorrect email or password.";
        handleMultipleWarnings(
                Arrays.asList(emailField, passwordField),
                Arrays.asList(emailWarning, passwordWarning),
                message
        );
    }

    private TranslateTransition createShakeAnimation(Object node) {
        TranslateTransition shake = new TranslateTransition(Duration.millis(100), (Node) node);
        shake.setByX(10);
        shake.setCycleCount(6);
        shake.setAutoReverse(true);
        return shake;
    }

    private FadeTransition createFadeOutAnimation(Object node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(2), (Node) node);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        return fade;
    }

    private FadeTransition createBorderFadeAnimation(TextField field) {
        FadeTransition fade = new FadeTransition(Duration.seconds(2), field);
        fade.setFromValue(1.0);
        fade.setToValue(1.0); // Keep field visible, but transition the border color
        fade.setOnFinished(event -> field.setStyle("-fx-border-color: #67608f; -fx-border-radius: 5;"));
        return fade;
    }

    public void setMainPaneController(MainPaneController mainPaneController) {
        this.mainPaneController = mainPaneController;
    }



    @FXML
    public void SignInSwap() {
        if (mainPaneController != null) {
            mainPaneController.loadSignUpPage();
        }
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

    @FXML
    public void initialize() {
        LogInButton.setOnAction(event -> {
            loadMainStructurePage();
        });
    }

    /**
     * Replaces the current stage with MainStructure.fxml.
     */
    @FXML
    private void loadMainStructurePage() {
        try {
            // Load the Loading Page
            String loadingPagePath = "/project/demo/FXMLLoginSignup/LoadingPage.fxml";
            FXMLLoader loadingLoader = new FXMLLoader(getClass().getResource(loadingPagePath));
            Parent loadingRoot = loadingLoader.load();

            // Get the current stage and set the Loading Page
            Stage stage = (Stage) LogInButton.getScene().getWindow();
            Scene loadingScene = new Scene(loadingRoot);
            stage.setScene(loadingScene);
            stage.centerOnScreen();

            System.out.println("[INFO] Displaying Loading Page with Fade-in...");

            // Apply Fade-In Transition
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), loadingRoot);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();

            // Simulate a delay for loading (e.g., 2 seconds) and switch to MainStructure.fxml
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> {
                try {
                    // Load MainStructure.fxml
                    String mainStructurePath = "/project/demo/MainStructure.fxml";
                    FXMLLoader mainLoader = new FXMLLoader(getClass().getResource(mainStructurePath));
                    Parent mainRoot = mainLoader.load();

                    // Replace the scene with MainStructure
                    stage.setScene(new Scene(mainRoot));
                    stage.centerOnScreen();
                    System.out.println("[INFO] Successfully loaded MainStructure.fxml");

                } catch (IOException e) {
                    System.err.println("[ERROR] Failed to load MainStructure.fxml");
                    e.printStackTrace();
                }
            });

            // Start the delay after Fade-In completes
            fadeIn.setOnFinished(event -> pause.play());

        } catch (IOException e) {
            System.err.println("[ERROR] Failed to load LoadingPage.fxml");
            e.printStackTrace();
        }
    }
}
