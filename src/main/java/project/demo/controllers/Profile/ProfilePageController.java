    package project.demo.controllers.Profile;

    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.control.Label;
    import javafx.scene.layout.AnchorPane;
    import javafx.scene.paint.ImagePattern;
    import javafx.scene.shape.Circle;
    import javafx.stage.FileChooser;
    import project.demo.DataBase.DatabaseConfig;
    import project.demo.models.UserSession;

    import java.io.File;
    import java.io.IOException;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;

    public class ProfilePageController {

        @FXML private Circle profileImageCircle; // For displaying profile image
        @FXML private Label nameLabel;           // User's name
        @FXML private Label emailLabel;          // User's email
        @FXML private AnchorPane contentPane;    // For loading different views

        private final DatabaseConfig db = new DatabaseConfig();
        private final String DEFAULT_IMAGE_PATH = "/project/demo/imagelogo/default.png  ";

        /**
         * Initializes the profile page.
         */
        @FXML
        public void initialize() {
            loadView("/project/demo/FXMLProfilePage/EditProfile.fxml"); // Default sub-view
            loadUserProfile(); // Load user details
        }

        /**
         * Fetches and displays user details (name, email, profile picture).
         */
        private void loadUserProfile() {
            UserSession session = UserSession.getInstance();
            String query = "SELECT username, email, profile_picture FROM users WHERE id = ?";

            try (Connection connection = db.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, session.getUserId());
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    // Populate name and email labels
                    nameLabel.setText(resultSet.getString("username") != null ? resultSet.getString("username") : "N/A");
                    emailLabel.setText(resultSet.getString("email") != null ? resultSet.getString("email") : "N/A");

                    // Set profile picture
                    String imagePath = resultSet.getString("profile_picture");
                    if (imagePath != null && !imagePath.isEmpty()) {
                        profileImageCircle.setFill(new ImagePattern(new javafx.scene.image.Image(imagePath)));
                    } else {
                        setDefaultProfileImage();
                    }
                } else {
                    System.err.println("[ERROR] No user found for ID: " + session.getUserId());
                    setDefaultProfileImage();
                }
            } catch (Exception e) {
                e.printStackTrace();
                setDefaultProfileImage();
            }
        }

        /**
         * Allows the user to select and change their profile picture.
         */
        @FXML
        public void handleImageChange() {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Profile Image");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

            File selectedFile = fileChooser.showOpenDialog(profileImageCircle.getScene().getWindow());
            if (selectedFile != null) {
                try {
                    // Define the target folder relative to source root
                    String uploadsDir = "src/main/resources/project/demo/pfp/";
                    File targetFolder = new File(uploadsDir);
                    if (!targetFolder.exists()) targetFolder.mkdirs(); // Create the folder if it doesn't exist

                    // Generate a unique filename based on user ID and the original filename
                    String newFileName = "profile_" + UserSession.getInstance().getUserId() + "_" + selectedFile.getName();
                    File destination = new File(targetFolder, newFileName);

                    // Copy the file to the uploads folder
                    java.nio.file.Files.copy(selectedFile.toPath(), destination.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                    // Save the relative path to the database
                    String relativePath = "/project/demo/pfp/" + newFileName; // Path relative to the source root
                    updateProfileImageInDatabase(relativePath);

                    // Update the Circle to show the new image
                    profileImageCircle.setFill(new ImagePattern(new javafx.scene.image.Image(
                            getClass().getResource("/" + relativePath).toExternalForm())));

                    System.out.println("[INFO] Profile image updated: " + relativePath);

                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("[ERROR] Failed to save profile image: " + e.getMessage());
                }
            }
        }


        /**
         * Updates the profile image path in the database.
         *
         * @param imagePath Path to the new image file.
         */
        private void updateProfileImageInDatabase(String imagePath) {
            UserSession session = UserSession.getInstance();
            String query = "UPDATE users SET profile_picture = ? WHERE id = ?";

            try (Connection connection = db.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, imagePath);
                statement.setInt(2, session.getUserId());
                statement.executeUpdate();
                System.out.println("[INFO] Profile picture updated successfully.");

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("[ERROR] Failed to update profile image: " + e.getMessage());
            }
        }

        /**
         * Sets the default profile image.
         */
        private void setDefaultProfileImage() {
            profileImageCircle.setFill(new ImagePattern(new javafx.scene.image.Image(
                    getClass().getResource(DEFAULT_IMAGE_PATH).toExternalForm())));
        }

        /**
         * Loads a new subview into the content pane.
         *
         * @param fxmlFilePath Path to the FXML file.
         */
        private void loadView(String fxmlFilePath) {
            try {
                AnchorPane newView = FXMLLoader.load(getClass().getResource(fxmlFilePath));
                contentPane.getChildren().clear();
                contentPane.getChildren().add(newView);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("[ERROR] Failed to load view: " + fxmlFilePath);
            }
        }

        /**
         * Navigation Handlers.
         */
        @FXML
        public void clickEditProfile() {
            loadView("/project/demo/FXMLProfilePage/EditProfile.fxml");
        }

        @FXML
        public void clickOrderHistory() {
            loadView("/project/demo/FXMLProfilePage/OrderHistory.fxml");
        }

        @FXML
        public void clickServiceHistory() {
            loadView("/project/demo/FXMLProfilePage/ServiceHistory.fxml");
        }

        @FXML
        public void clickManageAddresses() {
            loadView("/project/demo/FXMLProfilePage/ManageAddresses.fxml");
        }

        @FXML
        public void clickPaymentMethods() {
            loadView("/project/demo/FXMLProfilePage/PaymentFXML/PaymentMethods.fxml");
        }
    }
