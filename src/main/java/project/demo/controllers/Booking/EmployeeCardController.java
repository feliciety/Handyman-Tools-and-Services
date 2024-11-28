package project.demo.controllers.Booking;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.demo.models.Employee;

public class EmployeeCardController {

    @FXML
    private ImageView employeeImage;

    @FXML
    private Label employeeName;

    @FXML
    private Label employeeService;

    @FXML
    private Label employeeStatus;

    public void setEmployee(Employee employee) {
        employeeName.setText("Name: " + employee.getName());
        employeeService.setText("HomeService: " + employee.getSpecialization());
        employeeStatus.setText("Status: " + employee.getStatus());

        if (employee.getImage() != null) {
            employeeImage.setImage(new Image(getClass().getResourceAsStream(employee.getImage())));
        }
    }
}
