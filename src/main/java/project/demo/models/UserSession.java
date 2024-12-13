package project.demo.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

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
                return new Image(userImagePath); // Load user image from path
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to load user image: " + e.getMessage());
        }
        // Fallback to default image
        return new Image(getClass().getResource("/project/demo/imagelogo/user.png").toString());
    }

    // New logic: Integration with database actions
    /**
     * Retrieve the user ID for database queries.
     * Ensures that the user is logged in before proceeding.
     *
     * @return User ID or throws IllegalStateException if the session is not set.
     */
    public int retrieveUserIdForDatabase() {
        if (instance == null || getUserId() == 0) {
            throw new IllegalStateException("[ERROR] User session is not initialized.");
        }
        return getUserId();
    }

    /**
     * Populate session details from database or login operation.
     *
     * @param userId The user's ID.
     * @param username The user's name.
     * @param email The user's email address.
     * @param contactNumber The user's contact number.
     */
    public void populateSession(int userId, String username, String email, String contactNumber) {
        setUserId(userId);
        setUsername(username);
        setEmail(email);
        setContactNumber(contactNumber);
    }
}
