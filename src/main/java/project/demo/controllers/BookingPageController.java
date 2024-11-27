package project.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookingPageController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> timeComboBox;
    @FXML
    private RadioButton lowSeverity;
    @FXML
    private RadioButton mediumSeverity;
    @FXML
    private RadioButton highSeverity;
    @FXML
    private Button scheduleTimeButton;

    private ToggleGroup severityGroup;

    @FXML
    public void initialize() {
        // Initialize ToggleGroup for severity RadioButtons
        severityGroup = new ToggleGroup();
        lowSeverity.setToggleGroup(severityGroup);
        mediumSeverity.setToggleGroup(severityGroup);
        highSeverity.setToggleGroup(severityGroup);

        // Generate time slots and populate ComboBox
        timeComboBox.getItems().addAll(generateTimeSlots());

        // Set a prompt text in the DatePicker
        datePicker.setPromptText("Select a date");

        System.out.println("[INFO] Booking Page initialized with time slots and severity selection.");
    }

    @FXML
    private void handleScheduleTimeClick() {
        // Validate user input
        String name = nameField.getText();
        String address = addressField.getText();
        LocalDate date = datePicker.getValue();
        String time = timeComboBox.getValue();
        String severity = getSelectedSeverity();

        if (name == null || name.isEmpty() || address == null || address.isEmpty() ||
                date == null || time == null || severity == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "All fields must be filled out to schedule a time.");
            return;
        }

        // Format the date to "Month Day, Year"
        String formattedDate = formatDate(date);

        // Process scheduling
        String confirmationMessage = String.format("Service scheduled for %s at %s on %s (Severity: %s).",
                name, time, formattedDate, severity);
        showAlert(Alert.AlertType.INFORMATION, "Booking Confirmed", confirmationMessage);
        System.out.println(confirmationMessage);
    }

    private String getSelectedSeverity() {
        RadioButton selected = (RadioButton) severityGroup.getSelectedToggle();
        return selected != null ? selected.getText() : null;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private List<String> generateTimeSlots() {
        List<String> timeSlots = new ArrayList<>();
        int startHour = 8; // Starting hour: 8 AM
        int endHour = 17; // Ending hour: 5 PM

        for (int hour = startHour; hour <= endHour; hour++) {
            String period = hour < 12 ? "AM" : "PM";
            int displayHour = hour <= 12 ? hour : hour - 12;
            timeSlots.add(String.format("%d:00 %s", displayHour, period));
            if (hour != endHour) {
                timeSlots.add(String.format("%d:30 %s", displayHour, period));
            }
        }
        return timeSlots;
    }

    /**
     * Format the LocalDate to "Month Day, Year".
     *
     * @param date the LocalDate to format
     * @return a formatted date string
     */
    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        return date.format(formatter);
    }
}
