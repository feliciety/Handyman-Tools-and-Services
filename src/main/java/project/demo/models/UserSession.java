package project.demo.models;

import javafx.scene.image.Image;

public class UserSession {
    private static UserSession instance;

    private int userId;
    private String username;
    private String email;
    private String contactNumber;
    private String userImagePath; // Stores the file path for the profile picture

    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void clearSession() {
        instance = null;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getUserImagePath() {
        return userImagePath;
    }

    public void setUserImagePath(String userImagePath) {
        this.userImagePath = userImagePath;
    }

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
}
