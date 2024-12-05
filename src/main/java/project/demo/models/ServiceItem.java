package project.demo.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ServiceItem {

    private final int serviceId;
    private final Service service;  // Add a Service object
    private final SimpleDoubleProperty price;
    private final SimpleIntegerProperty quantity;

    private final double minPrice;
    private final double midPrice;
    private final double maxPrice;

    // Add a StringProperty to track job complexity
    private final SimpleStringProperty jobComplexity;

    public ServiceItem(int serviceId, Service service, double minPrice, double midPrice, double maxPrice) {
        this.serviceId = serviceId;
        this.service = service;  // Initialize the service object
        this.price = new SimpleDoubleProperty(minPrice); // Default to minPrice
        this.quantity = new SimpleIntegerProperty(1);
        this.minPrice = minPrice;
        this.midPrice = midPrice;
        this.maxPrice = maxPrice;

        // Initialize job complexity (default to "Low")
        this.jobComplexity = new SimpleStringProperty("Low");

        // Add a listener to update price based on job complexity
        this.jobComplexity.addListener((observable, oldValue, newValue) -> updatePriceForComplexity(newValue));
    }

    public int getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return service.getName();  // Access service data via the Service object
    }

    public String getServicePrice() {
        return service.getPrice();  // Access service data via the Service object
    }

    public String getServiceImagePath() {
        return service.getImagePath();  // Access service data via the Service object
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    // Getter and setter for job complexity
    public String getJobComplexity() {
        return jobComplexity.get();
    }

    public SimpleStringProperty jobComplexityProperty() {
        return jobComplexity;
    }

    public void setJobComplexity(String complexity) {
        this.jobComplexity.set(complexity);
    }

    // Updates the price based on the job complexity
    public void updatePriceForComplexity(String complexity) {
        switch (complexity) {
            case "Low":
                setPrice(minPrice);
                break;
            case "Medium":
                setPrice(midPrice);
                break;
            case "High":
                setPrice(maxPrice);
                break;
        }
    }

    public String getTotalPrice() {
        return String.format("%.2f", getPrice() * getQuantity());
    }
}
