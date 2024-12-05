package project.demo.controllers.Booking;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.demo.models.Service;
import project.demo.models.ServiceItem;

public class BookingCartTableController {

    @FXML
    private TableView<ServiceItem> cartTable;

    @FXML
    private TableColumn<ServiceItem, String> serviceImageCol, serviceNameCol, jobComplexityCol, serviceFeeCol, removeCol;

    private ObservableList<ServiceItem> serviceItems;

    private BookingPageController mainController; // Reference to main controller

    public void setMainController(BookingPageController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {



        serviceItems = FXCollections.observableArrayList();


        cartTable.setItems(serviceItems);

        // Initialize columns
        serviceImageCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiceImagePath()));  // Get the image path from ServiceItem
        serviceNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiceName()));  // Get the service name from ServiceItem
        jobComplexityCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJobComplexity()));  // Get job complexity from ServiceItem
        serviceFeeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTotalPrice()));  // Get the total price from ServiceItem

// You can initialize other columns as necessary
    }

        // Button navigation logic
    @FXML
    public void goToServices(ActionEvent event) {
        mainController.loadView("/project/demo/FXMLBookingPage/Services.fxml");
    }

    @FXML
    public void goToAddressBookingDetails(ActionEvent event) {
        mainController.loadView("/project/demo/FXMLBookingPage/AddressBookingDetails.fxml");
    }
}
