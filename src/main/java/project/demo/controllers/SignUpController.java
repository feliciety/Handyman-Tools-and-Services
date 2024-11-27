package project.demo.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SignUpController {

    public TextField emailField;
    public TextField passwordField;
    public Label warningLabel;
    public Button signUpButton;

    public void signUpButtonClicked(MouseEvent mouseEvent) {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Reset warning label
        warningLabel.setVisible(false);

        // Apply "loading" style and text
        signUpButton.getStyleClass().removeAll("error", "success");
        signUpButton.getStyleClass().add("loading");
        signUpButton.setText("Loading...");

        Timeline loadingTimeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    // Remove loading style after 3 seconds
                    signUpButton.getStyleClass().remove("loading");

                    if (email.isEmpty() || password.isEmpty() || !email.endsWith("@gmail.com")) {
                        // Validation failed
                        warningLabel.setText("Please fill all fields and use a valid Gmail address!");
                        warningLabel.setVisible(true);
                        signUpButton.setText("X");
                        signUpButton.getStyleClass().add("error");
                        resetButtonAfterFailure();
                    } else {
                        // Validation successful
                        signUpButton.setText("✓");
                        signUpButton.getStyleClass().add("success");
                        navigateToLoginPageAfterDelay();
                    }
                })
        );
        loadingTimeline.setCycleCount(1);
        loadingTimeline.play();
    }

    private void resetButtonAfterFailure() {
        Timeline resetTimeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    signUpButton.setText("SUBMIT");
                    signUpButton.getStyleClass().removeAll("error", "success");
                })
        );
        resetTimeline.setCycleCount(1);
        resetTimeline.play();
    }

    private void navigateToLoginPageAfterDelay() {
        Timeline navigationTimeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    Stage stage = (Stage) signUpButton.getScene().getWindow();
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/path/to/LogInPage.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        stage.setScene(scene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
        );
        navigationTimeline.setCycleCount(1);
        navigationTimeline.play();
    }

    public void signInSwap(MouseEvent mouseEvent) {
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/project/demo/FXMLLoginSignup/LogInPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
