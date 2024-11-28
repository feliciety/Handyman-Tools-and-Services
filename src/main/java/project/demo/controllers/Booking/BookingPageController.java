package project.demo.controllers.Booking;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import project.demo.models.Employee;

import java.io.IOException;

public class BookingPageController {

    @FXML
    private TableView<Employee> employeeTableView;
    @FXML
    private TableColumn<Employee, String> nameColumn;
    @FXML
    private TableColumn<Employee, String> serviceColumn;
    @FXML
    private TableColumn<Employee, String> statusColumn;
    @FXML
    private TableColumn<Employee, String> imageColumn;

    @FXML
    private ComboBox<String> timeComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private RadioButton lowSeverity;

    @FXML
    private RadioButton mediumSeverity;

    @FXML
    private RadioButton highSeverity;

    @FXML
    private Button bookServiceButton;

    @FXML
    private AnchorPane employeeCardContainer;


    @FXML
    public void initialize() {
        // Initialize TableView columns
        initializeTableColumns();

        // Populate time slots in ComboBox
        populateTimeSlots();

        // Load data into the TableView
        loadEmployeeData();

        // Add a listener to show EmployeeCard when an employee is selected
        employeeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showEmployeeCard(newValue);
            }
        });

        // Add a listener for the "Book HomeService" button
        bookServiceButton.setOnAction(event -> handleBookService());
    }

    private void initializeTableColumns() {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        serviceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSpecialization()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));

        // Set cell factory for image column to display images
        imageColumn.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);
                if (empty || imagePath == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(new Image(getClass().getResourceAsStream(imagePath)));
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    setGraphic(imageView);
                }
            }
        });
    }

    private void populateTimeSlots() {
        for (int hour = 8; hour <= 17; hour++) {
            String period = hour < 12 ? "AM" : "PM";
            int displayHour = hour <= 12 ? hour : hour - 12;
            timeComboBox.getItems().add(String.format("%d:00 %s", displayHour, period));
            if (hour != 17) {
                timeComboBox.getItems().add(String.format("%d:30 %s", displayHour, period));
            }
        }
    }

    private void loadEmployeeData() {
        employeeTableView.getItems().addAll(
                new Employee("Emily Anderson", "Appliance Repairer", "Available", "/project/demo/imagesemployee/ApplianceRepairer1.png"),
                new Employee("James Carter", "Appliance Repairer", "Available", "/project/demo/imagesemployee/ApplianceRepairer2.png"),
                new Employee("Michael Bennett", "Carpenter", "Available", "/project/demo/imagesemployee/Carpenter1.png"),
                new Employee("Daniel Johnson", "Carpenter", "Available", "/project/demo/imagesemployee/Carpenter2.png"),
                new Employee("Sarah Thompson", "Cleaner", "Available", "/project/demo/imagesemployee/Cleaner1.png"),
                new Employee("Emily Roberts", "Cleaner", "Available", "/project/demo/imagesemployee/Cleaner2.png"),
                new Employee("Ethan Wilson", "Electrician", "Available", "/project/demo/imagesemployee/Electrician1.png"),
                new Employee("Liam Cooper", "Electrician", "Available", "/project/demo/imagesemployee/Electrician2.png"),
                new Employee("Olivia Stewart", "Electrician", "Available", "/project/demo/imagesemployee/Electrician3.png"),
                new Employee("Ava Mitchell", "Flooring Specialist", "Available", "/project/demo/imagesemployee/Flooringspecialist1.png"),
                new Employee("Emma Scott", "Flooring Specialist", "Available", "/project/demo/imagesemployee/Flooringspecialist2.png"),
                new Employee("Benjamin Adams", "Mason", "Available", "/project/demo/imagesemployee/Mason1.png"),
                new Employee("Lucas Bailey", "Mason", "Available", "/project/demo/imagesemployee/Mason2.png"),
                new Employee("Sophia Morgan", "Mason", "Available", "/project/demo/imagesemployee/Mason3.png"),
                new Employee("Abigail Perez", "Painter", "Available", "/project/demo/imagesemployee/Painter1.png"),
                new Employee("Noah Wright", "Painter", "Available", "/project/demo/imagesemployee/Painter2.png"),
                new Employee("Charlotte Rivera", "Plumber", "Available", "/project/demo/imagesemployee/Plumber1.png"),
                new Employee("Henry Edwards", "Plumber", "Available", "/project/demo/imagesemployee/Plumber2.png"),
                new Employee("Oliver Phillips", "Plumber", "Available", "/project/demo/imagesemployee/Plumber3.png"),
                new Employee("Jacob Harris", "Plumber", "Available", "/project/demo/imagesemployee/Plumber4.png"),
                new Employee("Jack Davis", "Roofer", "Available", "/project/demo/imagesemployee/Roofer1.png"),
                new Employee("Andrew Clark", "Roofer", "Available", "/project/demo/imagesemployee/Roofer2.png"),
                new Employee("Amelia Lewis", "Roofing Specialist", "Available", "/project/demo/imagesemployee/RoofingSpecialist1.png"),
                new Employee("Grace Walker", "Roofing Specialist", "Available", "/project/demo/imagesemployee/RoofingSpecialist2.png")
        );
    }

    private void showEmployeeCard(Employee employee) {
        try {
            // Load the EmployeeCard FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLBookingPage/EmployeeCard.fxml"));
            VBox employeeCard = loader.load();

            // Get the controller for the FXML and pass the employee data
            EmployeeCardController controller = loader.getController();
            controller.setEmployee(employee);

            // Clear the container and add the employee card
            employeeCardContainer.getChildren().clear();
            employeeCardContainer.getChildren().add(employeeCard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    private void handleBookService() {
        // Validate required fields
        if (nameField.getText().isEmpty() || addressField.getText().isEmpty() || datePicker.getValue() == null || timeComboBox.getValue() == null) {
            showAlert("Missing Information", "Please fill out all the required fields before booking.");
            return;
        }

        // Get the selected severity
        String severity = lowSeverity.isSelected() ? "Low" :
                mediumSeverity.isSelected() ? "Medium" :
                        highSeverity.isSelected() ? "High" : "Not Specified";

        // Gather booking information
        String employeeName = employeeTableView.getSelectionModel().getSelectedItem().getName();
        String service = employeeTableView.getSelectionModel().getSelectedItem().getSpecialization();
        String name = nameField.getText();
        String address = addressField.getText();
        String date = datePicker.getValue().toString();
        String time = timeComboBox.getValue();

        // Display confirmation message
        String message = String.format(
                "Thank you for choosing Handyman Repair HomeService!\n\nBooking Details:\n" +
                        "HomeService: %s\nEmployee: %s\nName: %s\nAddress: %s\nDate & Time: %s %s\nSeverity: %s",
                service, employeeName, name, address, date, time, severity
        );
        showAlert("Booking Successful", message);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
