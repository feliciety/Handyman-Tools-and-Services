package project.demo.controllers.LoginSignup;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class MainPaneController {

    @FXML
    private AnchorPane contentPane;

    @FXML
    public void initialize() {
        loadLogInPage(); // Load LogInPage by default
    }

    public void loadSignUpPage() {
        loadPageWithLoadingScreen("/project/demo/FXMLLoginSignup/SignUpPage.fxml");
    }

    public void loadLogInPage() {
        loadPageWithLoadingScreen("/project/demo/FXMLLoginSignup/LogInPage.fxml");
    }

    /**
     * Displays a loading screen with fade transitions, then loads the target page.
     *
     * @param targetPagePath Path of the target FXML file.
     */
    private void loadPageWithLoadingScreen(String targetPagePath) {
        try {
            // Load the LoadingPage.fxml
            FXMLLoader loadingLoader = new FXMLLoader(getClass().getResource("/project/demo/FXMLLoginSignup/LoadingPage.fxml"));
            AnchorPane loadingPage = loadingLoader.load();

            // Apply fade-in transition for the loading page
            contentPane.getChildren().clear();
            contentPane.getChildren().add(loadingPage);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), loadingPage);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            // Pause transition to simulate loading duration
            PauseTransition pause = new PauseTransition(Duration.millis(2000));

            // Fade-out transition
            FadeTransition fadeOut = new FadeTransition(Duration.millis(500), loadingPage);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            // Chain transitions: Fade-in -> Pause -> Fade-out -> Load Target Page
            fadeIn.setOnFinished(event -> pause.play());
            pause.setOnFinished(event -> fadeOut.play());
            fadeOut.setOnFinished(event -> loadTargetPage(targetPagePath));

            fadeIn.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the target FXML page into the contentPane with a fade-in effect.
     *
     * @param fxmlPath Path of the target FXML file.
     */
    private void loadTargetPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane targetPage = loader.load();

            // Pass MainPaneController reference to the child controllers
            Object controller = loader.getController();
            if (controller instanceof SignUpPageController) {
                ((SignUpPageController) controller).setMainPaneController(this);
            } else if (controller instanceof LogInPageController) {
                ((LogInPageController) controller).setMainPaneController(this);
            }

            // Apply fade-in effect to the target page
            contentPane.getChildren().clear();
            contentPane.getChildren().add(targetPage);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), targetPage);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
