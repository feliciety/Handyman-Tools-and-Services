package project.demo.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class BookServiceItem {

    private final StringProperty serviceName = new SimpleStringProperty();
    private final StringProperty jobComplexity = new SimpleStringProperty(); // "high", "medium", "low"
    private final DoubleProperty serviceFee = new SimpleDoubleProperty(); // Service fee bound to the table
    private final StringProperty bookingDate = new SimpleStringProperty();
    private final ImageView serviceImageView; // Image for the service
    private final String serviceImage; // Image URL or path
    private double minPrice;
    private double maxPrice;
    private double midPrice;

    private final HBox jobComplexityControl; // Control for selecting complexity
    private final Button removeButton; // Remove button for the item

    // Constructor to initialize the service item
    public BookServiceItem(String serviceName, String jobComplexity, String priceRange, String bookingDate, String serviceImagePath) {
        this.serviceName.set(serviceName);
        this.jobComplexity.set(jobComplexity);
        this.bookingDate.set(bookingDate);
        this.serviceImage = serviceImagePath;

        // Set the initial price values and service fee, it will be updated when the price range is parsed in the controller
        this.minPrice = 0.0;
        this.maxPrice = 0.0;
        this.midPrice = 0.0;

        // Load service image
        this.serviceImageView = createServiceImageView(serviceImagePath);

        // Create the job complexity control
        this.jobComplexityControl = createJobComplexityControl();

        // Create the remove button
        this.removeButton = createRemoveButton();
    }

    // Calculate the service fee based on job complexity
    private double calculateServiceFee() {
        double fee = 0.0;
        switch (jobComplexity.get().toLowerCase()) {
            case "high":
                fee = maxPrice; // Use max price for high complexity
                break;
            case "medium":
                fee = (minPrice + maxPrice) / 2; // Average price for medium complexity
                fee = Math.round(fee * 100.0) / 100.0; // Round to two decimal places
                break;
            case "low":
                fee = minPrice; // Use min price for low complexity
                break;
            default:
                throw new IllegalArgumentException("Invalid job complexity: " + jobComplexity.get());
        }

        return fee;
    }

    // Create service image view
    private ImageView createServiceImageView(String serviceImagePath) {
        ImageView imageView = new ImageView();
        try {
            Image image = new Image(getClass().getResource(serviceImagePath).toExternalForm());
            imageView.setImage(image);
            imageView.setFitWidth(50); // Adjust width
            imageView.setFitHeight(50); // Adjust height
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("[ERROR] Unable to load image: " + serviceImagePath);
            e.printStackTrace();
        }
        return imageView;
    }

    // Create job complexity control with up/down buttons
    private HBox createJobComplexityControl() {
        Text complexityLabel = new Text(jobComplexity.get());
        Button upButton = new Button("+");
        Button downButton = new Button("-");

        // Style buttons
        upButton.setStyle("-fx-background-color: #ffdb00; -fx-text-fill: #000000; -fx-font-weight: bold;");
        downButton.setStyle("-fx-background-color: #ffdb00; -fx-text-fill: #000000; -fx-font-weight: bold;");

        // Handle up button click
        upButton.setOnAction(event -> {
            if (jobComplexity.get().equals("low")) {
                setJobComplexity("medium");
            } else if (jobComplexity.get().equals("medium")) {
                setJobComplexity("high");
            }
            complexityLabel.setText(jobComplexity.get()); // Update label
        });

        // Handle down button click
        downButton.setOnAction(event -> {
            if (jobComplexity.get().equals("high")) {
                setJobComplexity("medium");
            } else if (jobComplexity.get().equals("medium")) {
                setJobComplexity("low");
            }
            complexityLabel.setText(jobComplexity.get()); // Update label
        });

        HBox complexityBox = new HBox(10, downButton, complexityLabel, upButton);
        complexityBox.setSpacing(10);
        complexityBox.setStyle("-fx-alignment: center;"); // Center alignment
        return complexityBox;
    }

    // Create remove button
    private Button createRemoveButton() {
        Button button = new Button("X");
        button.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        button.setOnAction(event -> {
            System.out.println("[DEBUG] Remove button clicked for: " + getServiceName());
        });
        return button;
    }

    // Getters and setters for properties
    public String getServiceName() {
        return serviceName.get();
    }

    public void setServiceName(String serviceName) {
        this.serviceName.set(serviceName);
    }

    public StringProperty serviceNameProperty() {
        return serviceName;
    }

    public String getJobComplexity() {
        return jobComplexity.get();
    }

    public void setJobComplexity(String jobComplexity) {
        this.jobComplexity.set(jobComplexity);
        this.serviceFee.set(calculateServiceFee()); // Recalculate and update the service fee
    }

    public StringProperty jobComplexityProperty() {
        return jobComplexity;
    }

    public double getServiceFee() {
        return serviceFee.get();
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee.set(serviceFee);
    }

    public DoubleProperty serviceFeeProperty() {
        return serviceFee;
    }

    public String getBookingDate() {
        return bookingDate.get();
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate.set(bookingDate);
    }

    public StringProperty bookingDateProperty() {
        return bookingDate;
    }

    public ImageView getServiceImageView() {
        return serviceImageView;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public HBox getJobComplexityControl() {
        return jobComplexityControl;
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    // Getter for min price
    public double getMinPrice() {
        return minPrice;
    }

    // Getter for max price
    public double getMaxPrice() {
        return maxPrice;
    }

    // Getter for mid price
    public double getMidPrice() {
        return midPrice;
    }

    // Setter for min price
    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    // Setter for max price
    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    // Setter for mid price
    public void setMidPrice(double midPrice) {
        this.midPrice = midPrice;
    }
}
