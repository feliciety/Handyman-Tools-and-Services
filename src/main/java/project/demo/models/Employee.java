package project.demo.models;

import javafx.beans.property.*;
import javafx.scene.image.ImageView;

public class Employee {

    private final ObjectProperty<ImageView> image;
    private final StringProperty name;
    private final StringProperty service;
    private final StringProperty status;

    // Constructor
    public Employee(ImageView image, String name, String service, String status) {
        this.image = new SimpleObjectProperty<>(image);
        this.name = new SimpleStringProperty(name);
        this.service = new SimpleStringProperty(service);
        this.status = new SimpleStringProperty(status);
    }

    // Getters for properties
    public ObjectProperty<ImageView> imageProperty() {
        return image;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty serviceProperty() {
        return service;
    }

    public StringProperty statusProperty() {
        return status;
    }

    // Getters for values
    public ImageView getImage() {
        return image.get();
    }

    public String getName() {
        return name.get();
    }

    public String getService() {
        return service.get();
    }

    public String getStatus() {
        return status.get();
    }

    // Setters
    public void setImage(ImageView image) {
        this.image.set(image);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setService(String service) {
        this.service.set(service);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
