package project.demo.models;

public class Employee {
    private final String name;
    private final String specialization;
    private final String status;
    private final String image;

    public Employee(String name, String specialization, String status, String image) {
        this.name = name;
        this.specialization = specialization;
        this.status = status;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }
}
