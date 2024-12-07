package project.demo.models;

public class Employee {
    private int employeeId;
    private String name;
    private String role;
    private String status;
    private String description;
    private String profilePicture;

    public Employee(int employeeId, String name, String role, String status, String description, String profilePicture) {
        this.employeeId = employeeId;
        this.name = name;
        this.role = role;
        this.statusdfeddcve = status;
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
