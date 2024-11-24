package project.demo.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.ImageView;

public class Employee {
    private ObjectProperty<ImageView> image;
    private StringProperty name;
    private StringProperty service;
    private StringProperty status;

    public Employee(ImageView image, String name, String service, String status) {
        this.image = new SimpleObjectProperty<>(image);
        this.name = new SimpleStringProperty(name);
        this.service = new SimpleStringProperty(service);
        this.status = new SimpleStringProperty(status);
    }

    public ImageView getImage() {
        return image.get();
    }

    public ObjectProperty<ImageView> imageProperty() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image.set(image);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getService() {
        return service.get();
    }

    public StringProperty serviceProperty() {
        return service;
    }

    public void setService(String service) {
        this.service.set(service);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
