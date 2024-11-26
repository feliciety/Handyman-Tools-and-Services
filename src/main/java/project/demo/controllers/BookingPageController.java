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
                new Employee(new ImageView(getClass().getResource("/imagesemployee/ApplianceRepairer1.png").toExternalForm()), "John Doe", "Appliance Repair", "Available"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/ApplianceRepairer2.png").toExternalForm()), "Alice Johnson", "Appliance Repair", "Busy"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/Carpenter1.png").toExternalForm()), "Jane Smith", "Carpentry", "Available"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/Carpenter2.png").toExternalForm()), "Bob Brown", "Carpentry", "Busy"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/Carpenter3.png").toExternalForm()), "Michael Davis", "Carpentry", "Available"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/Cleaner1.png").toExternalForm()), "Emily White", "Cleaning", "Available"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/Cleaner2.png").toExternalForm()), "Olivia Lewis", "Cleaning", "Busy"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/Electrician1.png").toExternalForm()), "James Clark", "Electrical", "Available"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/Electrician2.png").toExternalForm()), "Sophia Robinson", "Electrical", "Busy"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/Electrician3.png").toExternalForm()), "Daniel Wilson", "Electrical", "Available"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/FlooringSpecialist1.png").toExternalForm()), "Ethan Thomas", "Flooring", "Available"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/FlooringSpecialist2.png").toExternalForm()), "Mia Hall", "Flooring", "Busy"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/Mason1.png").toExternalForm()), "Alexander Young", "Masonry", "Available"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/Mason2.png").toExternalForm()), "Charlotte Adams", "Masonry", "Busy"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/Mason3.png").toExternalForm()), "Benjamin Lee", "Masonry", "Available"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/Painter1.png").toExternalForm()), "Isabella Scott", "Painting", "Available"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/Painter2.png").toExternalForm()), "Elijah Harris", "Painting", "Busy"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/Plumber1.png").toExternalForm()), "William Martin", "Plumbing", "Available"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/Plumber2.png").toExternalForm()), "Ava Allen", "Plumbing", "Busy"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/Plumber3.png").toExternalForm()), "Lucas Wright", "Plumbing", "Available"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/Plumber4.png").toExternalForm()), "Amelia Baker", "Plumbing", "Busy"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/Roofer1.png").toExternalForm()), "Henry Gonzalez", "Roofing", "Available"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/Roofer2.png").toExternalForm()), "Harper Perez", "Roofing", "Busy"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/RoofingSpecialist1.png").toExternalForm()), "Liam Sanchez", "Roofing", "Available"),
                new Employee(new ImageView(getClass().getResource("/imagesemployee/RoofingSpecialist2.png").toExternalForm()), "Ella Ramirez", "Roofing", "Busy")
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
