package project.demo.controllers.Profile;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import project.demo.models.UserSession;

import java.io.IOException;

public class ProfilePageController {

    @FXML
    private AnchorPane contentPane; // Dynamic content pane for loading views

    @FXML
    private Label UserName; // Label to display the user's name

    @FXML
    private Label userEmail; // Label to display the user's email

    /**
     * Initializes the profile page by loading the default view (Edit Profile).
     */
    @FXML
    public void initialize() {
        // Load the Edit Profile view by default
        loadView("/project/demo/FXMLProfilePage/EditProfile.fxml");

        // Fetch and display logged-in user information
        UserSession session = UserSession.getInstance();
        UserName.setText(session.getUsername());
        userEmail.setText(session.getEmail());

        if (session.getUserImage() != null) {
            userImage.setImage(session.getUserImage());
        }

        System.out.println("[INFO] Logged-in User ID: " + session.getUserId());
        System.out.println("[INFO] Username: " + session.getUsername());
        System.out.println("[INFO] Email: " + session.getEmail());
    }

    /**
     * Dynamically loads a new view into the content pane.
     *
     * @param fxmlPath The FXML file path to load.
     */
    public void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane newView = loader.load();

            // Set main controller in subcontrollers dynamically
            Object controller = loader.getController();
            if (controller instanceof EditProfileController) {
                EditProfileController editProfileController = (EditProfileController) controller;
                editProfileController.setMainController(this);

                // Populate user details
                UserSession session = UserSession.getInstance();
                editProfileController.initializeUserDetails(
                        session.getUsername(),
                        session.getEmail(),
                        session.getContactNumber()
                );
            } else if (controller instanceof OrderHistoryController) {
                ((OrderHistoryController) controller).setMainController(this);
            } else if (controller instanceof EditAddressController) {
                ((EditAddressController) controller).setMainController(this);
            } else if (controller instanceof EditPaymentMethodsController) {
                ((EditPaymentMethodsController) controller).setMainController(this);
            }

            // Clear and replace content pane
            contentPane.getChildren().clear();
            contentPane.getChildren().add(newView);

            // Anchor the new view
            AnchorPane.setTopAnchor(newView, 0.0);
            AnchorPane.setBottomAnchor(newView, 0.0);
            AnchorPane.setLeftAnchor(newView, 0.0);
            AnchorPane.setRightAnchor(newView, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to load view: " + fxmlPath);
        }
    }

    /**
     * Handle the "Edit Profile" button click event.
     */
    @FXML
    public void clickEditProfile() {
        loadView("/project/demo/FXMLProfilePage/EditProfile.fxml");
    }

    /**
     * Handle the "Order History" button click event.
     */
    @FXML
    public void clickOrderHistory() {
        loadView("/project/demo/FXMLProfilePage/OrderHistory.fxml");
    }

    /**
     * Handle the "Manage Addresses" button click event.
     */
    @FXML
    public void clickManageAddresses() {
        loadView("/project/demo/FXMLProfilePage/EditAddress.fxml");
    }

    /**
     * Handle the "Payment Methods" button click event.
     */
    @FXML
    public void clickPaymentMethods() {
        loadView("/project/demo/FXMLProfilePage/EditPaymentMethods.fxml");
    }

    /**
     * Updates profile details for the logged-in user.
     *
     * @param newName          Updated name
     * @param newEmail         Updated email
     * @param newContactNumber Updated contact number
     */
    public void updateProfileDetails(String newName, String newEmail, String newContactNumber) {
        if (newName == null || newName.isEmpty()) {
            System.err.println("[ERROR] Name cannot be null or empty.");
            return;
        }
        if (newEmail == null || newEmail.isEmpty()) {
            System.err.println("[ERROR] Email cannot be null or empty.");
            return;
        }
        if (newContactNumber == null || newContactNumber.isEmpty()) {
            System.err.println("[ERROR] Contact number cannot be null or empty.");
            return;
        }

        try {
            // Update session details
            UserSession session = UserSession.getInstance();
            session.setUsername(newName);
            session.setEmail(newEmail);
            session.setContactNumber(newContactNumber);

            // Log updates for debugging
            System.out.println("[INFO] Profile details updated successfully:");
            System.out.println("Name: " + newName);
            System.out.println("Email: " + newEmail);
            System.out.println("Contact Number: " + newContactNumber);

        } catch (Exception e) {
            System.err.println("[ERROR] Failed to update profile details: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
