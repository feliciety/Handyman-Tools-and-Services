package project.demo.models;

public class Employee {
    private final int employeeId;
    private final String name;
    private final String role;
    private final String status;
    private final String description;
    private final String profilePicture;

    public Employee(int employeeId, String name, String role, String status, String description, String profilePicture) {
        this.employeeId = employeeId;
        this.name = name;
        this.role = role;
        this.status = status;
        this.description = description;
        this.profilePicture = profilePicture;
    }

    public int getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
    public String getDescription() { return description; }
    public String getProfilePicture() { return profilePicture; }
}
