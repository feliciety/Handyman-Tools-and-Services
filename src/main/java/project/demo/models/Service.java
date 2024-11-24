package project.demo.models;

public class Service {
    private String name;
    private String description;
    private String shortDescription;
    private String imagePath;

    public Service(String name, String description, String shortDescription, String imagePath) {
        this.name = name;
        this.description = description;
        this.shortDescription = shortDescription;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getImagePath() {
        return imagePath;
    }
}
