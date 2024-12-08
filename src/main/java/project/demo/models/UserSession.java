package project.demo.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.io.File;

public class UserSession {
    private static UserSession instance;

    private final IntegerProperty userId = new SimpleIntegerProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty contactNumber = new SimpleStringProperty();
    private String userImagePath; // Stores the file path for the profile picture

    // Constructor is private for Singleton pattern
    private UserSession() {
    }

    // Singleton instance accessor
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Method to clear session data
    public void clearSession() {
        instance = null;
    }

    // Real-time properties
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

    // Getters and setters for userId
    public int getUserId() {
        return userId.get();
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    // Getters and setters for username
    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    // Getters and setters for email
    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    // Getters and setters for contactNumber
    public String getContactNumber() {
        return contactNumber.get();
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber.set(contactNumber);
    }

    // Getters and setters for userImagePath
    public String getUserImagePath() {
        return userImagePath;
    }

    public void setUserImagePath(String userImagePath) {
        this.userImagePath = userImagePath;
    }

    // Method to get the user's profile image
    public Image getUserImage() {
        try {
            if (userImagePath != null && !userImagePath.isEmpty()) {
                File profileImageFile = new File(userImagePath);
                if (profileImageFile.exists()) {
                    return new Image(profileImageFile.toURI().toString());
                } else {
                    System.err.println("[WARNING] Profile image path invalid. Using default image.");
                }
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to load user image: " + e.getMessage());
        }
        // Fallback to default image
        return new Image(getClass().getResource("/project/demo/imagelogo/user.png").toString());
    }

    // New Logic: Method to validate user session
    public boolean isSessionValid() {
        return getUserId() > 0 && getUsername() != null && !getUsername().isEmpty();
    }

    // New Logic: Update profile picture path and refresh the session
    public boolean updateProfileImagePath(String newPath) {
        try {
            File imageFile = new File(newPath);
            if (imageFile.exists()) {
                setUserImagePath(newPath);
                System.out.println("[INFO] Profile image updated successfully.");
                return true;
            } else {
                System.err.println("[ERROR] Profile image path does not exist: " + newPath);
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to update profile image: " + e.getMessage());
        }
        return false;
    }

    // New Logic: Integration to validate session before performing an action
    public void ensureValidSession() {
        if (!isSessionValid()) {
            throw new IllegalStateException("[ERROR] User session is not initialized or invalid.");
        }
    }

    /**
     * Retrieve the user ID for database queries.
     * Ensures that the user is logged in before proceeding.
     *
     * @return User ID or throws IllegalStateException if the session is not set.
     */
    public int retrieveUserIdForDatabase() {
        ensureValidSession();
        return getUserId();
    }

    /**
     * Populate session details from database or login operation.
     *
     * @param userId The user's ID.
     * @param username The user's name.
     * @param email The user's email address.
     * @param contactNumber The user's contact number.
     * @param userImagePath The user's profile picture path.
     */
    public void populateSession(int userId, String username, String email, String contactNumber, String userImagePath) {
        setUserId(userId);
        setUsername(username);
        setEmail(email);
        setContactNumber(contactNumber);
        setUserImagePath(userImagePath);
    }

    public void setUserImage(Image image) {
    }
}
