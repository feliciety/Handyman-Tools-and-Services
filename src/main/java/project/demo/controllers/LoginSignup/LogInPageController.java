package project.demo.controllers.LoginSignup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.demo.DataBase.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LogInPageController {

    @FXML
    private TextField emailOrUsernameField; // Updated field name to reflect its dual purpose

    @FXML
    private PasswordField passwordField;

    private final DatabaseConfig db = new DatabaseConfig();

    @FXML
    private void LogInButtonClicked() {
        String emailOrUsername = emailOrUsernameField.getText();
        String password = passwordField.getText();

        if (emailOrUsername.isEmpty() || password.isEmpty()) {
            System.out.println("Please fill in all fields.");
            return;
        }

        // Query checks both email and username
        String query = "SELECT * FROM users WHERE (email = ? OR username = ?) AND password = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, emailOrUsername);
            statement.setString(2, emailOrUsername);
            statement.setString(3, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Login Success! Welcome!");
                navigateToPage("/project/demo/MainStructure.fxml", "Main Application");
            } else {
                System.out.println("Invalid email/username or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database.");
        }
    }

    @FXML
    public void signUpSwap() {
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
}
