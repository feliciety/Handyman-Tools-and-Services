package project.demo.controllers.Employee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import project.demo.DataBase.DatabaseConfig;
import project.demo.models.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeesPageController {

    public VBox employeeDetailsCard;
    public Button searchButton;
    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, String> nameColumn;
    @FXML private TableColumn<Employee, String> serviceColumn;
    @FXML private TableColumn<Employee, String> statusColumn;
    @FXML private TableColumn<Employee, String> phoneColumn;

    @FXML private ImageView profilePicture;
    @FXML private Label employeeName, serviceName, statusBadge;
    @FXML private TextArea employeeDescription;
    @FXML private Button bookNowButton;

    @FXML private ComboBox<String> availabilityFilter, serviceCategoryFilter;
    @FXML private TextField searchField;

    private final ObservableList<Employee> employeeList = FXCollections.observableArrayList();

    // Database config
    private final DatabaseConfig databaseConfig = new DatabaseConfig();

    public void initialize() {
        setupTableColumns();
        loadAllEmployees();
        setupFilters();

        // Adjust row height
        employeeTable.setRowFactory(tv -> {
            TableRow<Employee> row = new TableRow<>() {
                @Override
                protected void updateItem(Employee item, boolean empty) {
                    super.updateItem(item, empty);
                    setPrefHeight(50); // Set row height here
                }
            };
            return row;
        });
    }


    private void setupTableColumns() {
        nameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        serviceColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getRole()));
        statusColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));
        phoneColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPhoneNumber()));

        statusColumn.setCellFactory(col -> new TableCell<Employee, String>() {
            private final Label statusLabel = new Label();

            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);

                if (empty || status == null) {
                    setGraphic(null);
                } else {
                    // Configure the label styling
                    statusLabel.setText(status);
                    statusLabel.setStyle(getStatusStyle(status));
                    statusLabel.setMinWidth(100);
                    statusLabel.setPrefHeight(30);
                    statusLabel.setAlignment(javafx.geometry.Pos.CENTER); // Center the label content
                    setStyle("-fx-alignment: CENTER;"); // Center the cell itself

                    setGraphic(statusLabel);
                }
            }

            // Dynamic styling based on status
            private String getStatusStyle(String status) {
                if ("Available".equalsIgnoreCase(status)) {
                    return "-fx-background-color: #E0F8E0; " + // Light green background
                            "-fx-border-color: #90EE90; " +    // Light green border
                            "-fx-border-width: 2px; " +
                            "-fx-border-radius: 15; " +
                            "-fx-background-radius: 15; " +
                            "-fx-text-fill: #2E8B57; " +       // SeaGreen text
                            "-fx-font-weight: normal; " +      // Regular font
                            "-fx-font-size: 10px; " +          // Font size 10
                            "-fx-padding: 5;";
                } else if ("Unavailable".equalsIgnoreCase(status)) {
                    return "-fx-background-color: #FDEDEC; " + // Light red background
                            "-fx-border-color: #F08080; " +    // Light coral border
                            "-fx-border-width: 2px; " +
                            "-fx-border-radius: 15; " +
                            "-fx-background-radius: 15; " +
                            "-fx-text-fill: #CD5C5C; " +       // IndianRed text
                            "-fx-font-weight: normal; " +      // Regular font
                            "-fx-font-size: 10px; " +          // Font size 10
                            "-fx-padding: 5;";
                }
                return "-fx-background-color: #F0F0F0; " +    // Light gray background
                        "-fx-border-color: #C0C0C0; " +        // Gray border
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 15; " +
                        "-fx-background-radius: 15; " +
                        "-fx-text-fill: #808080; " +           // Dark gray text
                        "-fx-font-weight: normal; " +          // Regular font
                        "-fx-font-size: 10px; " +              // Font size 10
                        "-fx-padding: 5;";
            }
            });

        // Listener for row selection
        employeeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) displayEmployeeDetails(newSelection);
        });
    }

    private <T> void centerAlignColumn(TableColumn<Employee, T> column) {
        column.setCellFactory(tc -> new TableCell<Employee, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.toString());
                    setStyle("-fx-alignment: CENTER;"); // Center alignment
                }
            }
        });
    }


    private void loadAllEmployees() {
        employeeList.clear();
        String query = "SELECT employee.*, role.role_name FROM employee INNER JOIN role ON employee.role_id = role.role_id";
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
                        rs.getString("profile_picture"),
                        rs.getString("phone_number")
                ));
            }
            employeeTable.setItems(employeeList);
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
                        rs.getString("profile_picture"),
                        rs.getString("phone_number")
                ));
            }
            employeeTable.setItems(employeeList);
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
                        rs.getString("profile_picture"),
                        rs.getString("phone_number")
                ));
            }
            employeeTable.setItems(employeeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayEmployeeDetails(Employee employee) {
        employeeName.setText("Name: " + employee.getName());
        serviceName.setText("Role: " + employee.getRole());
        statusBadge.setText("Status: " + employee.getStatus());
        employeeDescription.setText(employee.getDescription());
        System.out.println(employee.getProfilePicture());
        profilePicture.setImage(new Image(getClass().getResource("/"+employee.getProfilePicture()).toExternalForm()));

        bookNowButton.setDisable(!"Available".equals(employee.getStatus()));
    }

    @FXML
    private void onBookNowClicked(ActionEvent event) {
        System.out.println("Service booked for: " + employeeName.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Service booked successfully!", ButtonType.OK);
        alert.showAndWait();
    }



}
