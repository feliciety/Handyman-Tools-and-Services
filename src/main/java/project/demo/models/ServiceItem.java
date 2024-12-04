package project.demo.models;

import javafx.beans.property.*;

public class ServiceItem {

    private final IntegerProperty serviceId;
    private final StringProperty serviceName;
    private final StringProperty jobComplexity;
    private final DoubleProperty selectedPrice;
    private final StringProperty imageFilePath;

    public ServiceItem(int serviceId, String serviceName, String jobComplexity, double selectedPrice, String imageFilePath) {
        this.serviceId = new SimpleIntegerProperty(serviceId);
        this.serviceName = new SimpleStringProperty(serviceName);
        this.jobComplexity = new SimpleStringProperty(jobComplexity);
        this.selectedPrice = new SimpleDoubleProperty(selectedPrice);
        this.imageFilePath = new SimpleStringProperty(imageFilePath);
    }

    // Getters and setters for serviceId
    public int getServiceId() {
        return serviceId.get();
    }

    public void setServiceId(int serviceId) {
        this.serviceId.set(serviceId);
    }

    public IntegerProperty serviceIdProperty() {
        return serviceId;
    }

    // Getters and setters for serviceName
    public String getServiceName() {
        return serviceName.get();
    }

    public void setServiceName(String serviceName) {
        this.serviceName.set(serviceName);
    }

    public StringProperty serviceNameProperty() {
        return serviceName;
    }

    // Getters and setters for jobComplexity
    public String getJobComplexity() {
        return jobComplexity.get();
    }

    public void setJobComplexity(String jobComplexity) {
        this.jobComplexity.set(jobComplexity);
    }

    public StringProperty jobComplexityProperty() {
        return jobComplexity;
    }

    // Getters and setters for selectedPrice
    public double getSelectedPrice() {
        return selectedPrice.get();
    }

    public void setSelectedPrice(double selectedPrice) {
        this.selectedPrice.set(selectedPrice);
    }

    public DoubleProperty selectedPriceProperty() {
        return selectedPrice;
    }

    // Getters and setters for imageFilePath
    public String getImageFilePath() {
        return imageFilePath.get();
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath.set(imageFilePath);
    }

    public StringProperty imageFilePathProperty() {
        return imageFilePath;
    }
}
