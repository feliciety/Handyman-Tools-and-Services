package project.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import project.demo.models.Employee;

public class BookingPageController {

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, ImageView> imageColumn;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> serviceColumn;

    @FXML
    private TableColumn<Employee, String> statusColumn;

    @FXML
    private VBox bookingPane;

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> timeComboBox;

    @FXML
    private ToggleGroup severityGroup;

    @FXML
    private Button bookServiceButton;

    private ObservableList<Employee> employeeList;

    @FXML
    public void initialize() {
        // Populate table columns
        imageColumn.setCellValueFactory(data -> data.getValue().imageProperty());
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        serviceColumn.setCellValueFactory(data -> data.getValue().serviceProperty());
        statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());

        // Load employee data
        employeeList = FXCollections.observableArrayList(
//                new Employee(new ImageView("/project/demo/imagesemployee/Electrician.png"), "John Doe", "Plumbing", "Available"),
                new Employee(new ImageView("/project/demo/imagesemployee/Electrician.png"), "Jane Smith", "Carpentry", "Busy"),
                new Employee(new ImageView("/project/demo/imagesemployee/Electrician.png"), "Bob Brown", "Electrical", "Available")
        );
        employeeTable.setItems(employeeList);

        // Populate time combo box
        timeComboBox.setItems(FXCollections.observableArrayList("9:00 AM", "10:00 AM", "11:00 AM", "1:00 PM", "2:00 PM", "3:00 PM"));

        // Handle employee selection
        employeeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleEmployeeSelection(newSelection);
            }
        });

        // Book service button action
        bookServiceButton.setOnAction(event -> handleBookService());
    }

    private void handleEmployeeSelection(Employee employee) {
        // Logic for updating bookingPane with the selected employee's information
        System.out.println("Selected Employee: " + employee.getName());
    }

    private void handleBookService() {
        String name = nameField.getText();
        String address = addressField.getText();
        String date = (datePicker.getValue() != null) ? datePicker.getValue().toString() : "No date selected";
        String time = timeComboBox.getValue();
        String severity = ((RadioButton) severityGroup.getSelectedToggle()).getText();

        // Validate input
        if (name.isEmpty() || address.isEmpty() || time == null || severity == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Incomplete Form");
            alert.setContentText("Please fill all fields before booking a service.");
            alert.showAndWait();
            return;
        }

        // Process booking
        System.out.println("Booking Details:");
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Severity: " + severity);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Booking Successful");
        alert.setContentText("Your service has been booked successfully!");
        alert.showAndWait();
    }
}
