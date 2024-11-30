package project.demo.controllers.Profile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import project.demo.DataBase.DatabaseConfig;
import project.demo.controllers.Cart.CartPageController;
import project.demo.models.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;

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

    private int userId; // Current user's ID, set by the main profile controller
    private ProfilePageController mainController; // Reference to the main profile page controller
    private final DatabaseConfig db = new DatabaseConfig();

    /**
     * Sets the main controller (ProfilePageController) and user ID.
     *
     * @param profilePageController Reference to the ProfilePageController
     * @param userId                Current user's ID
     */
    public void initialize(ProfilePageController profilePageController, int userId) {
        this.mainController = profilePageController;
        this.userId = userId;

    }

    public void setMainController(ProfilePageController mainController) {
        this.mainController = mainController;
    }

    /**
     * Handles the Save Changes button click event.
     * Updates user details in the database.
     */
    /**
     * Handles the Save Changes button click event.
     * Validates input and updates the database if valid.
     */

    @FXML
    public void handleSaveChanges() {
        resetWarnings();

        String newName = nameField.getText().trim();
        String newEmail = emailField.getText().trim();
        String newContactNumber = contactNumberField.getText().trim();

        // Fetch current session details
        UserSession session = UserSession.getInstance();
        String currentName = session.getUsername();
        String currentEmail = session.getEmail();
        String currentContactNumber = session.getContactNumber();

        // Check if any value has changed
        if (newName.equals(currentName) && newEmail.equals(currentEmail) && newContactNumber.equals(currentContactNumber)) {
            System.out.println("[INFO] No changes detected in the input fields.");
            return;
        }

        // Proceed with validation and update
        boolean isValid = validateInputs(newName, newEmail, newContactNumber);

        if (!isValid) {
            System.out.println("[ERROR] Validation failed. Please check the warnings.");
            return;
        }

        // Update profile in the database
        try (Connection connection = db.getConnection()) {
            String query = "UPDATE users SET username = ?, email = ?, contact_number = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newName);
            statement.setString(2, newEmail);
            statement.setString(3, newContactNumber);
            statement.setInt(4, session.getUserId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("[INFO] Profile updated successfully.");

                // Update UserSession
                session.setUsername(newName);
                session.setEmail(newEmail);
                session.setContactNumber(newContactNumber);
            } else {
                System.out.println("[WARNING] No changes were made to your profile.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[ERROR] Failed to update profile. Database error.");
        }
    }



    /**
     * Validates the input fields and shows warnings if invalid.
     *
     * @param name          The user's name
     * @param email         The user's email
     * @param contactNumber The user's phone number
     * @return true if all inputs are valid, false otherwise
     */
    private boolean validateInputs(String name, String email, String contactNumber) {
        boolean isValid = true;

        if (name.isEmpty()) {
            nameWarningLabel.setVisible(true);
            System.out.println("[ERROR] Name is required.");
            isValid = false;
        }

        if (email.isEmpty() || !email.matches("^\\S+@\\S+\\.\\S+$")) {
            emailWarningLabel.setVisible(true);
            System.out.println("[ERROR] Valid email is required.");
            isValid = false;
        }

        if (contactNumber.isEmpty() || !contactNumber.matches("\\d{10,15}")) {
            phoneWarningLabel.setVisible(true);
            System.out.println("[ERROR] Valid phone number is required.");
            isValid = false;
        }

        return isValid;
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
     * Initializes the EditProfileController with user details.
     *
     * @param username      The user's username
     * @param email         The user's email
     * @param contactNumber The user's contact number
     */
    public void initializeUserDetails(String username, String email, String contactNumber) {
        if (username != null) {
            nameField.setText(username);
        }
        if (email != null) {
            emailField.setText(email);
        }
        if (contactNumber != null) {
            contactNumberField.setText(contactNumber);
        }
    }

}
