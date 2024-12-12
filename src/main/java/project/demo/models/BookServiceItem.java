package project.demo.models;

import javafx.beans.property.*;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.beans.binding.StringBinding;

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

    private Runnable onRemoveAction; // Callback for row deletion

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

    // Set the callback for row removal
    public void setOnRemoveAction(Runnable onRemoveAction) {
        this.onRemoveAction = onRemoveAction;
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
    // Calculate the service fee based on job complexity
    private double calculateServiceFee() {
        return switch (jobComplexity.get().toLowerCase()) {
            case "high" -> Math.round(maxPrice * 100.0) / 100.0; // Round to 2 decimal places
            case "medium" -> Math.round(((minPrice + maxPrice) / 2) * 100.0) / 100.0; // Midpoint
            case "low" -> Math.round(minPrice * 100.0) / 100.0;
            default -> 0.0;
        };
    }

    // New method to return formatted service fee as String
    public StringBinding formattedServiceFeeProperty() {
        // Dynamically bind the formatted service fee with peso sign
        return new StringBinding() {
            {
                bind(serviceFee); // Bind to the DoubleProperty serviceFee
            }

            @Override
            protected String computeValue() {
                return String.format("₱%.2f", serviceFee.get());
            }
        };
    }


    public void setJobComplexity(String complexity) {
        this.jobComplexity.set(complexity);
        this.serviceFee.set(calculateServiceFee()); // Recalculate service fee
    }
    // Create service image view
    private ImageView createServiceImageView(String serviceImagePath) {
        ImageView imageView = new ImageView();
        try {
            Image image = new Image(getClass().getResource(serviceImagePath).toExternalForm());
            imageView.setImage(image);
            imageView.setFitWidth(75);
            imageView.setFitHeight(75);
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

        // Increase job complexity
        upButton.setOnAction(event -> {
            if (jobComplexity.get().equals("low")) setJobComplexity("medium");
            else if (jobComplexity.get().equals("medium")) setJobComplexity("high");
            complexityLabel.setText(jobComplexity.get());
            updateServiceFee(); // Update the service fee in real time
        });

        // Decrease job complexity
        downButton.setOnAction(event -> {
            if (jobComplexity.get().equals("high")) setJobComplexity("medium");
            else if (jobComplexity.get().equals("medium")) setJobComplexity("low");
            complexityLabel.setText(jobComplexity.get());
            updateServiceFee(); // Update the service fee in real time
        });

        return new HBox(5, downButton, complexityLabel, upButton);
    }

    // Update service fee when job complexity changes
    private void updateServiceFee() {
        this.serviceFee.set(calculateServiceFee());
    }


    // Create remove button
    private Button createRemoveButton() {
        Button button = new Button("X");
        button.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        button.setOnAction(event -> {
            if (onRemoveAction != null) {
                System.out.println("[DEBUG] Remove button clicked for: " + getServiceName());
                onRemoveAction.run(); // Trigger removal callback
            } else {
                System.out.println("[ERROR] onRemoveAction is not set for: " + getServiceName());
            }
        });
        return button;
    }



    // Getters for table properties
    public String getServiceName() {
        return serviceName.get();
    }

    public ImageView getServiceImageView() {
        return serviceImageView;
    }

    public HBox getJobComplexityControl() {
        return jobComplexityControl;
    }


    public double getServiceFee() {
        return serviceFee.get();
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    public DatePicker getBookingDatePicker() {
        return bookingDatePicker;
    }

    // Property methods for TableView binding
    public StringProperty serviceNameProperty() {
        return serviceName;
    }

    public DoubleProperty serviceFeeProperty() {
        return serviceFee;
    }

    public ObjectProperty<LocalDate> bookingDateProperty() {
        return bookingDate;
    }


    public void setBookingDate(String bookingDate) {
        if (bookingDate != null && !bookingDate.isEmpty()) {
            this.bookingDate.set(LocalDate.parse(bookingDate));
        } else {
            this.bookingDate.set(LocalDate.now());
        }
    }
    // Restored jobComplexityProperty
    public StringProperty jobComplexityProperty() {
        return jobComplexity; // Return the job complexity property
    }


    // Getter for service fee with peso sign for display purposes
    public String getFormattedServiceFee() {
        return String.format("₱%.2f", serviceFee.get());
    }



}
