package project.demo.controllers.Booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class BookingCartTableController {

    @FXML
    private TableView<?> cartTable;

    @FXML
    private TableColumn<?, ?> serviceImageCol, serviceNameCol, jobComplexityCol, serviceFeeCol, bookDateCol, removeCol;

    private BookingPageController mainController; // Reference to main controller

    public void setMainController(BookingPageController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void goToServices(ActionEvent event) {
        mainController.loadView("/project/demo/FXMLBookingPage/Services.fxml");
    }

    @FXML
    public void goToAddressBookingDetails(ActionEvent event) {
        mainController.loadView("/project/demo/FXMLBookingPage/AddressBookingDetails.fxml");
    }
}
