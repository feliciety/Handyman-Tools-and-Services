package project.demo.models;

public class Employee {
    private int id;
    private String name;
    private String role;
    private String status;
    private String description;
    private String profilePicture;
    private String phoneNumber;
    private String serviceName; // New field for service name

    // Constructor with serviceName
    public Employee(int id, String name, String role, String status, String description,
                    String profilePicture, String phoneNumber, String serviceName) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.status = status;
        this.description = description;
        this.profilePicture = profilePicture;
        this.phoneNumber = phoneNumber;
        this.serviceName = serviceName;
    }

    // Getters and Setters
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

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

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
