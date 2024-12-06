package project.demo.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class BookServiceItem {

    private final StringProperty serviceName = new SimpleStringProperty();
    private final StringProperty jobComplexity = new SimpleStringProperty(); // "high", "medium", "low"
    private double serviceFee; // Calculated fee
    private final StringProperty bookingDate = new SimpleStringProperty();
    private final ImageView serviceImageView; // Image for the service
    private String serviceImage; // Image URL or path
    private double minPrice;
    private double maxPrice;

    private final HBox jobComplexityControl; // Control for selecting complexity
    private final Button removeButton; // Remove button for the item

    public BookServiceItem(String serviceName, String jobComplexity, String priceRange, String bookingDate, String serviceImagePath) {
        this.serviceName.set(serviceName);
        this.jobComplexity.set(jobComplexity);
        this.bookingDate.set(bookingDate);
        this.serviceImage = serviceImagePath;

        // Parse price range
        if (priceRange.contains("-")) {
            String[] prices = priceRange.split("-");
            this.minPrice = Double.parseDouble(prices[0].trim());
            this.maxPrice = Double.parseDouble(prices[1].trim());
        } else {
            this.minPrice = Double.parseDouble(priceRange.trim());
            this.maxPrice = this.minPrice; // Single price case
        }

        // Calculate service fee based on job complexity
        this.serviceFee = calculateServiceFee();

        // Load service image
        this.serviceImageView = createServiceImageView(serviceImagePath);

        // Create the job complexity control
        this.jobComplexityControl = createJobComplexityControl();

        // Create the remove button
        this.removeButton = createRemoveButton();
    }

    private double calculateServiceFee() {
        switch (jobComplexity.get().toLowerCase()) {
            case "high":
                return maxPrice; // Use max price for high complexity
            case "medium":
                return (minPrice + maxPrice) / 2; // Average price for medium complexity
            case "low":
                return minPrice; // Use min price for low complexity
            default:
                throw new IllegalArgumentException("Invalid job complexity: " + jobComplexity.get());
        }
    }

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

    private HBox createJobComplexityControl() {
        Text complexityLabel = new Text(jobComplexity.get());
        Button upButton = new Button("+");
        Button downButton = new Button("-");

        // Style buttons (optional)
        upButton.setStyle("-fx-background-color: #00ff00; -fx-text-fill: white; -fx-font-weight: bold;");
        downButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white; -fx-font-weight: bold;");

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
        complexityBox.setSpacing(5);
        return complexityBox;
    }

    private Button createRemoveButton() {
        Button button = new Button("X");
        button.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        button.setOnAction(event -> {
            // Logic for removing this item will be handled by the controller
            System.out.println("[DEBUG] Remove button clicked for: " + getServiceName());
        });
        return button;
    }

    // Getters and setters
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
        this.serviceFee = calculateServiceFee(); // Recalculate fee
    }

    public StringProperty jobComplexityProperty() {
        return jobComplexity;
    }

    public double getServiceFee() {
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

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public HBox getJobComplexityControl() {
        return jobComplexityControl;
    }

    public Button getRemoveButton() {
        return removeButton;
    }
}
