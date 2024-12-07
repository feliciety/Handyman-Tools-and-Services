package project.demo.controllers.Employee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.demo.DataBase.DatabaseConfig;
import project.demo.models.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeesPageController {

    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, String> profileColumn;
    @FXML private TableColumn<Employee, String> nameColumn;
    @FXML private TableColumn<Employee, String> serviceColumn;
    @FXML private TableColumn<Employee, String> statusColumn;

    @FXML private ImageView profilePicture;
    @FXML private Label employeeName, serviceName, statusBadge;
    @FXML private TextArea employeeDescription;
    @FXML private Button bookNowButton;

    @FXML private ComboBox<String> availabilityFilter, serviceCategoryFilter;
    @FXML private TextField searchField;
    @FXML private Label filterStatusLabel;

    private ObservableList<Employee> employeeList = FXCollections.observableArrayList();

    // Database config
    private DatabaseConfig databaseConfig = new DatabaseConfig();

    public void initialize() {
        setupTableColumns();
        loadAllEmployees();
        setupFilters();
    }

    private void setupTableColumns() {
        profileColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty("Image"));
        nameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        serviceColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getRole()));
        statusColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));

        employeeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) displayEmployeeDetails(newSelection);
        });
    }

    private void loadAllEmployees() {
        employeeList.clear();
        String query = "SELECT * FROM employee INNER JOIN role ON employee.role_id = role.role_id";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                employeeList.add(new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("name"),
                        rs.getString("role_name"),
                        rs.getString("status"),
                        rs.getString("description"),
                        rs.getString("profile_picture")
                ));
            }
            employeeTable.setItems(employeeList);
            filterStatusLabel.setText("All Employees");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupFilters() {
        availabilityFilter.setOnAction(event -> filterEmployees());
        serviceCategoryFilter.setOnAction(event -> filterEmployees());
        searchField.setOnAction(event -> onSearchClicked());
    }

    @FXML
    private void onSearchClicked() {
        String searchText = searchField.getText().trim();
        employeeList.clear();
        String query = "SELECT * FROM employee INNER JOIN role ON employee.role_id = role.role_id WHERE name LIKE ?";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + searchText + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                employeeList.add(new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("name"),
                        rs.getString("role_name"),
                        rs.getString("status"),
                        rs.getString("description"),
                        rs.getString("profile_picture")
                ));
            }
            employeeTable.setItems(employeeList);
            filterStatusLabel.setText("Search Results: " + searchText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterEmployees() {
        String availability = availabilityFilter.getValue();
        String role = serviceCategoryFilter.getValue();

        employeeList.clear();
        StringBuilder query = new StringBuilder("SELECT * FROM employee INNER JOIN role ON employee.role_id = role.role_id WHERE 1=1");
        if (availability != null) query.append(" AND status = '").append(availability).append("'");
        if (role != null) query.append(" AND role_name = '").append(role).append("'");

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString());
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                employeeList.add(new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("name"),
                        rs.getString("role_name"),
                        rs.getString("status"),
                        rs.getString("description"),
                        rs.getString("profile_picture")
                ));
            }
            employeeTable.setItems(employeeList);
            filterStatusLabel.setText("Filtered Results");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayEmployeeDetails(Employee employee) {
        employeeName.setText("Name: " + employee.getName());
        serviceName.setText("Role: " + employee.getRole());
        statusBadge.setText("Status: " + employee.getStatus());
        employeeDescription.setText(employee.getDescription());
        profilePicture.setImage(new Image("file:" + employee.getProfilePicture()));

        bookNowButton.setDisable(!"Available".equals(employee.getStatus()));
    }

    @FXML
    private void onBookNowClicked(ActionEvent event) {
        System.out.println("Service booked for: " + employeeName.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Service booked successfully!", ButtonType.OK);
        alert.showAndWait();
    }
}
