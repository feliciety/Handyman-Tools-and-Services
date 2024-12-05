package project.demo.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ServiceItem {

    private final Service service;
    private final StringProperty jobComplexity;
    private final DoubleProperty serviceFee;


    private final Button decreaseButton;
    private final Button increaseButton;
    private final Label serviceFeeLabel;
    private final HBox complexityControl;

    public ServiceItem(Service service) {
        this.service = service;

        // Initialize job complexity to "Low" and service fee to min price
        this.jobComplexity = new SimpleStringProperty("Low");
        this.serviceFee = new SimpleDoubleProperty(service.getMinPrice());


        // Create the complexity control (buttons for Low, Medium, High)
        this.decreaseButton = new Button("-");
        this.increaseButton = new Button("+");
        this.serviceFeeLabel = new Label(formatPrice(serviceFee.get()));

        // HBox for buttons and label
        this.complexityControl = new HBox(decreaseButton, serviceFeeLabel, increaseButton);
        this.complexityControl.setSpacing(5);

        // Button logic to decrease complexity
        decreaseButton.setOnAction(event -> {
            decreaseComplexity();
        });

        // Button logic to increase complexity
        increaseButton.setOnAction(event -> {
            increaseComplexity();
        });
    }

    // Public Getters
    public String getJobComplexity() {
        return jobComplexity.get();
    }

    public Double getServiceFee() {
        return serviceFee.get();
    }

    public ImageView getServiceImage() {
        return serviceImage;
    }

    public HBox getComplexityControl() {
        return complexityControl;
    }

    // Public Methods
    public void increaseComplexity() {
        switch (jobComplexity.get()) {
            case "Low":
                jobComplexity.set("Medium");
                serviceFee.set(service.getMidPrice());
                break;
            case "Medium":
                jobComplexity.set("High");
                serviceFee.set(service.getMaxPrice());
                break;
            case "High":
                return; // Maximum complexity, do nothing
        }
        updateServiceFee();
    }

    public void decreaseComplexity() {
        switch (jobComplexity.get()) {
            case "High":
                jobComplexity.set("Medium");
                serviceFee.set(service.getMidPrice());
                break;
            case "Medium":
                jobComplexity.set("Low");
                serviceFee.set(service.getMinPrice());
                break;
            case "Low":
                return; // Minimum complexity, do nothing
        }
        updateServiceFee();
    }

    // Private helper method to update service fee label
    private void updateServiceFee() {
        serviceFeeLabel.setText(formatPrice(serviceFee.get()));
    }

    private String formatPrice(double price) {
        return String.format("$%.2f", price);
    }
}
