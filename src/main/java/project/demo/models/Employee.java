package project.demo.models;

public class Employee {
    private int id;
    private String name;
    private String role;
    private String status;
    private String description;
    private String profilePicture;

    // Constructor
    public Employee(int id, String name, String role, String status, String description, String profilePicture) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.status = status;
        this.description = description;
        this.profilePicture = profilePicture;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }
}
