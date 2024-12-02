package project.demo.controllers.Profile;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import project.demo.DataBase.DatabaseConfig;
import project.demo.models.UserSession;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EditProfileController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField contactNumberField;

    @FXML
    private Label nameWarningLabel;

    @FXML
    private Label emailWarningLabel;

    @FXML
    private Label phoneWarningLabel;

    @FXML
    private Label saveSuccessLabel;

    private final DatabaseConfig db = new DatabaseConfig();

    @FXML
    public void initialize() {
        // Populate user details from the database into the fields
        populateUserDetails();

        // Set success and warning labels to be initially hidden
        saveSuccessLabel.setVisible(false);
        resetWarnings();
    }

    /**
     * Populates user details from the database into the corresponding fields.
     */
    private void populateUserDetails() {
        UserSession session = UserSession.getInstance();

        try (Connection connection = db.getConnection()) {
            String query = "SELECT username, email, contact_number FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, session.getUserId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nameField.setText(resultSet.getString("username"));
                emailField.setText(resultSet.getString("email"));
                contactNumberField.setText(resultSet.getString("contact_number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to fetch user details: " + e.getMessage());
        }
    }

    /**
     * Handles saving changes made to the user's profile.
     */
    @FXML
    public void handleSaveChanges() {
        resetWarnings();

        String newName = nameField.getText().trim();
        String newEmail = emailField.getText().trim();
        String newContactNumber = contactNumberField.getText().trim();

        if (!validateInputs(newName, newEmail, newContactNumber)) {
            animateWarningLabels(); // Trigger animations for warning labels
            return;
        }

        try (Connection connection = db.getConnection()) {
            String query = "UPDATE users SET username = ?, email = ?, contact_number = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newName);
            statement.setString(2, newEmail);
            statement.setString(3, newContactNumber);
            statement.setInt(4, UserSession.getInstance().getUserId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                // Update session with new values
                UserSession session = UserSession.getInstance();
                session.setUsername(newName);
                session.setEmail(newEmail);
                session.setContactNumber(newContactNumber);

                triggerSuccessAnimation();
            } else {
                System.out.println("[WARNING] No changes were made.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to update profile: " + e.getMessage());
        }
    }

    /**
     * Resets all warning labels to be hidden.
     */
    private void resetWarnings() {
        nameWarningLabel.setVisible(false);
        emailWarningLabel.setVisible(false);
        phoneWarningLabel.setVisible(false);
    }

    /**
     * Validates the inputs in the fields for correctness.
     *
     * @param name         User's name.
     * @param email        User's email.
     * @param contactNumber User's contact number.
     * @return True if all inputs are valid, false otherwise.
     */
    private boolean validateInputs(String name, String email, String contactNumber) {
        boolean isValid = true;

        if (name.isEmpty()) {
            nameWarningLabel.setVisible(true);
            isValid = false;
        }

        if (email.isEmpty() || !email.matches("^\\S+@\\S+\\.\\S+$")) {
            emailWarningLabel.setVisible(true);
            isValid = false;
        }

        if (contactNumber.isEmpty() || !contactNumber.matches("\\d{10,15}")) {
            phoneWarningLabel.setVisible(true);
            isValid = false;
        }

        return isValid;
    }

    /**
     * Animates the warning labels with fade-in and fade-out effects.
     */
    private void animateWarningLabels() {
        if (nameWarningLabel.isVisible()) animateLabel(nameWarningLabel);
        if (emailWarningLabel.isVisible()) animateLabel(emailWarningLabel);
        if (phoneWarningLabel.isVisible()) animateLabel(phoneWarningLabel);
    }

    /**
     * Triggers a success animation when the save operation is successful.
     */
    private void triggerSuccessAnimation() {
        animateLabel(saveSuccessLabel);
    }

    /**
     * Handles updating the user's profile image.
     */
    @FXML
    public void handleImageChange(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(nameField.getScene().getWindow());
        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();

            try (Connection connection = db.getConnection()) {
                String query = "UPDATE users SET profile_image_path = ? WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, imagePath);
                statement.setInt(2, UserSession.getInstance().getUserId());
                statement.executeUpdate();

                System.out.println("[INFO] Profile image updated.");
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("[ERROR] Failed to update profile image: " + e.getMessage());
            }
        }
    }

    /**
     * Generic method to animate a label with fade-in and fade-out effects.
     *
     * @param label The label to animate.
     */
    private void animateLabel(Label label) {
        label.setVisible(true);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), label);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), label);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setDelay(Duration.seconds(2.5));

        fadeIn.setOnFinished(event -> fadeOut.play());
        fadeIn.play();
    }
}
