package project.demo.models;

public class Service {
    private final String name;
    private final String description;
    private final String price;
    private final String imagePath;

    public Service(String name, String description, String price, String imagePath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }
}
