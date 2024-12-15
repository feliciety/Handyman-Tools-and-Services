package project.demo.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class UserSession {
    // Singleton instance
    private static UserSession instance;

    // Properties for user data
    private final IntegerProperty userId = new SimpleIntegerProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty contactNumber = new SimpleStringProperty();
    private String userImagePath; // Stores the file path for the profile picture

    // Private constructor for Singleton pattern
    private UserSession() {
    }

    /**
     * Get the singleton instance of UserSession.
     * Ensures only one instance of the session is used.
     *
     * @return the singleton instance of UserSession
     */
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    /**
     * Clear the session data. Used during logout.
     */
    public void clearSession() {
        // Clear existing instance
        instance = null;
    }

    // Properties for JavaFX bindings
    public IntegerProperty userIdProperty() {
        return userId;
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty contactNumberProperty() {
        return contactNumber;
    }

    // Getters and setters for User ID
    public int getUserId() {
        return userId.get();
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    // Getters and setters for Username
    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    // Getters and setters for Email
    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    // Getters and setters for Contact Number
    public String getContactNumber() {
        return contactNumber.get();
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber.set(contactNumber);
    }

    // Getters and setters for the User's Profile Image Path
    public String getUserImagePath() {
        return userImagePath;
    }

    public void setUserImagePath(String userImagePath) {
        this.userImagePath = userImagePath;
    }

    /**
     * Load the user's profile image.
     * If the image path is invalid or empty, load a default fallback image.
     *
     * @return Image object of the user's profile picture
     */
    public Image getUserImage() {
        try {
            if (userImagePath != null && !userImagePath.isEmpty()) {
                return new Image(userImagePath); // Load user image from specified path
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to load user image: " + e.getMessage());
        }
        // Fallback to default image
        return new Image(getClass().getResource("/project/demo/imagelogo/user.png").toString());
    }

    /**
     * Retrieve the User ID for database-related queries.
     * Ensures that a valid session is active before proceeding.
     *
     * @return the user's ID
     * @throws IllegalStateException if no valid session is found
     */
    public int retrieveUserIdForDatabase() {
        if (getUserId() == 0) {
            throw new IllegalStateException("[ERROR] User session is not initialized. Please log in.");
        }
        return getUserId();
    }

    /**
     * Populate the session with user data after a successful login.
     * This should be called immediately upon successful login to set session details.
     *
     * @param userId        The user's unique ID
     * @param username      The username of the user
     * @param email         The email address of the user
     * @param contactNumber The contact number of the user
     */
    public void populateSession(int userId, String username, String email, String contactNumber) {
        setUserId(userId);
        setUsername(username);
        setEmail(email);
        setContactNumber(contactNumber);
    }
}