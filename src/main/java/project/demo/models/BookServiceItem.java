package project.demo.models;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class BookServiceItem {

    private Service service;
    private final StringProperty serviceName = new SimpleStringProperty();
    private final StringProperty jobComplexity = new SimpleStringProperty(); // "high", "medium", "low"
    private final DoubleProperty serviceFee = new SimpleDoubleProperty(); // Service fee bound to the table
    private final ObjectProperty<LocalDate> bookingDate = new SimpleObjectProperty<>(); // Use LocalDate for date handling
    private final ImageView serviceImageView; // Image for the service
    private final String serviceImage; // Image URL or path
    private double minPrice;
    private double maxPrice;
    private double midPrice;

    private final HBox jobComplexityControl; // Control for selecting complexity
    private final Button removeButton; // Remove button for the item
    private final DatePicker bookingDatePicker; // DatePicker for selecting booking date

    // Constructor to initialize the service item
    public BookServiceItem(Service service, String jobComplexity, String bookingDate) {
        this.service = service;
        this.serviceName.set(service.getName());
        this.jobComplexity.set(jobComplexity);
        this.bookingDate.set(bookingDate != null && !bookingDate.isEmpty() ? LocalDate.parse(bookingDate) : LocalDate.now());
        this.serviceImage = service.getImagePath();

        // Set the initial price values and service fee based on the Service object
        this.minPrice = service.getMinPrice();
        this.maxPrice = service.getMaxPrice();
        this.midPrice = (minPrice + maxPrice) / 2;

        // Calculate the service fee based on job complexity
        this.serviceFee.set(calculateServiceFee());

        // Load service image
        this.serviceImageView = createServiceImageView(service.getImagePath());

        // Create the job complexity control
        this.jobComplexityControl = createJobComplexityControl();

        // Create the DatePicker control
        this.bookingDatePicker = createDatePicker();

        // Create the remove button
        this.removeButton = createRemoveButton();
    }

    // Create a DatePicker control for the booking date
    private DatePicker createDatePicker() {
        DatePicker datePicker = new DatePicker(bookingDate.get());
        datePicker.setOnAction(event -> {
            bookingDate.set(datePicker.getValue());
            System.out.println("Booking Date Updated: " + datePicker.getValue());
        });
        return datePicker;
    }

    // Calculate the service fee based on job complexity
    private double calculateServiceFee() {
        double fee = switch (jobComplexity.get().toLowerCase()) {
            case "high" -> maxPrice;
            case "medium" -> Math.round((minPrice + maxPrice) / 2 * 100.0) / 100.0;
            default -> minPrice;
        };
        return fee;
    }

    // Create service image view
    private ImageView createServiceImageView(String serviceImagePath) {
        ImageView imageView = new ImageView();
        try {
            Image image = new Image(getClass().getResource(serviceImagePath).toExternalForm());
            imageView.setImage(image);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("[ERROR] Unable to load image: " + serviceImagePath);
        }
        return imageView;
    }

    // Create job complexity control with up/down buttons
    private HBox createJobComplexityControl() {
        Text complexityLabel = new Text(jobComplexity.get());
        Button upButton = new Button("+");
        Button downButton = new Button("-");

        upButton.setOnAction(event -> {
            if (jobComplexity.get().equals("low")) setJobComplexity("medium");
            else if (jobComplexity.get().equals("medium")) setJobComplexity("high");
            complexityLabel.setText(jobComplexity.get());
        });

        downButton.setOnAction(event -> {
            if (jobComplexity.get().equals("high")) setJobComplexity("medium");
            else if (jobComplexity.get().equals("medium")) setJobComplexity("low");
            complexityLabel.setText(jobComplexity.get());
        });

        return new HBox(10, downButton, complexityLabel, upButton);
    }

    // Create remove button
    private Button createRemoveButton() {
        Button button = new Button("X");
        button.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        button.setOnAction(event -> System.out.println("[DEBUG] Remove button clicked for: " + getServiceName()));
        return button;
    }

    // Getters for table properties
    public String getServiceName() { return serviceName.get(); }
    public ImageView getServiceImageView() { return serviceImageView; }
    public HBox getJobComplexityControl() { return jobComplexityControl; }
    public double getServiceFee() { return serviceFee.get(); }
    public Button getRemoveButton() { return removeButton; }
    public DatePicker getBookingDatePicker() { return bookingDatePicker; } // Expose DatePicker for TableView

    // Property methods for TableView binding
    public StringProperty serviceNameProperty() { return serviceName; }
    public DoubleProperty serviceFeeProperty() { return serviceFee; }
    public ObjectProperty<LocalDate> bookingDateProperty() { return bookingDate; }

    public void setJobComplexity(String complexity) {
        this.jobComplexity.set(complexity);
        this.serviceFee.set(calculateServiceFee());
    }



    // Restored jobComplexityProperty
    public StringProperty jobComplexityProperty() {
        return jobComplexity; // Return the job complexity property
    }

    // Restored setBookingDate logic
    public void setBookingDate(String bookingDate) {
        if (bookingDate != null && !bookingDate.isEmpty()) {
            this.bookingDate.set(LocalDate.parse(bookingDate)); // Update the booking date property
        } else {
            this.bookingDate.set(LocalDate.parse(LocalDate.now().toString())); // Default to the current date
        }
    }

}
