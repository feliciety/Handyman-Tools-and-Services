package project.demo.models;

public class ServiceCardModel {
    private String name;
    private String description;
    private String price;
    private String imagePath;

    public ServiceCardModel(String name, String description, String price, String imagePath) {
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
