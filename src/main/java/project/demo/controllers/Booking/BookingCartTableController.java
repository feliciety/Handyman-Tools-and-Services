package project.demo.controllers.Booking;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import project.demo.models.BookServiceItem;
import project.demo.models.BookServiceManager;
import javafx.event.ActionEvent;


public class BookingCartTableController {

    @FXML
    private TableView<BookServiceItem> bookingTable;

    @FXML
    private TableColumn<BookServiceItem, ImageView> serviceImageCol;

    @FXML
    private TableColumn<BookServiceItem, String> serviceNameCol;

    @FXML
    private TableColumn<BookServiceItem, HBox> jobComplexityCol;

    @FXML
    private TableColumn<BookServiceItem, Double> serviceFeeCol;

    @FXML
    private TableColumn<BookServiceItem, String> bookingDateCol;

    @FXML
    private TableColumn<BookServiceItem, Button> deleteButtonCol;

    private final ObservableList<BookServiceItem> bookedItems = BookServiceManager.getInstance().getBookedServices();

    private BookingPageController mainController;

    @FXML
    public void initialize() {
        System.out.println("[DEBUG] Initializing BookingCartTableController.");

        // Set up table columns with centered alignment
        serviceImageCol.setCellValueFactory(new PropertyValueFactory<>("serviceImageView"));
        serviceNameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        jobComplexityCol.setCellValueFactory(new PropertyValueFactory<>("jobComplexityControl"));
        serviceFeeCol.setCellValueFactory(new PropertyValueFactory<>("serviceFee"));
        bookingDateCol.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        deleteButtonCol.setCellValueFactory(new PropertyValueFactory<>("removeButton"));

        // Center align all columns
        centerColumnContent(serviceImageCol);
        centerColumnContent(serviceNameCol);
        centerHBoxContent(jobComplexityCol);
        centerColumnContent(serviceFeeCol);
        centerColumnContent(bookingDateCol);
        centerButtonContent(deleteButtonCol);

        // Bind the bookedItems from BookServiceManager to the table
        bookingTable.setItems(bookedItems);

        System.out.println("[DEBUG] BookingCartTableController initialized.");
    }

    /**
     * Centers the content of a TableColumn with generic content.
     */
    private <T> void centerColumnContent(TableColumn<BookServiceItem, T> column) {
        column.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else if (item instanceof Node) {
                    setGraphic((Node) item);
                    setText(null);
                } else {
                    setText(item.toString());
                    setGraphic(null);
                }
                setAlignment(Pos.CENTER); // Center align content
            }
        });
    }

    /**
     * Centers the content of a TableColumn with HBox content.
     */
    private void centerHBoxContent(TableColumn<BookServiceItem, HBox> column) {
        column.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(HBox hbox, boolean empty) {
                super.updateItem(hbox, empty);
                if (empty || hbox == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    hbox.setAlignment(Pos.CENTER); // Center align the HBox itself
                    setGraphic(hbox);
                    setText(null);
                }
                setAlignment(Pos.CENTER); // Center align the cell
            }
        });
    }

    /**
     * Centers the content of a TableColumn with Button content.
     */
    private void centerButtonContent(TableColumn<BookServiceItem, Button> column) {
        column.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Button button, boolean empty) {
                super.updateItem(button, empty);
                if (empty || button == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setGraphic(button);
                    button.setAlignment(Pos.CENTER); // Center align the button
                    setText(null);
                }
                setAlignment(Pos.CENTER); // Center align the cell
            }
        });
    }

    /**
     * Removes a booked service item from the table.
     */
    public void removeBookedService(BookServiceItem serviceItem) {
        System.out.println("[DEBUG] Removing service: " + serviceItem.getServiceName());
        bookedItems.remove(serviceItem);
        updateTable();
    }

    /**
     * Refreshes the table to reflect updates.
     */
    public void updateTable() {
        System.out.println("[DEBUG] Refreshing Booking Table.");
        bookingTable.refresh();
    }

    /**
     * Adds a service to the booking table.
     */
    public void addService(BookServiceItem serviceItem) {
        System.out.println("[DEBUG] Adding service to table: " + serviceItem.getServiceName());
        BookServiceManager.getInstance().addService(serviceItem);
        updateTable();
    }

    /**
     * Sets the reference to the main controller.
     */
    public void setMainController(BookingPageController mainController) {
        this.mainController = mainController;
    }

    /**
     * Navigates to the Services page.
     */
    public void goToServices(ActionEvent actionEvent) {
        if (mainController != null) {
            System.out.println("[DEBUG] Navigating to Services page.");
            mainController.loadView("/project/demo/FXMLBookingPage/BookingCartTable.fxml");
        } else {
            System.err.println("[ERROR] Main controller is not set!");
        }
    }

    /**
     * Navigates to the Address Booking Details page.
     */
    public void goToAddressBookingDetails(ActionEvent actionEvent) {
        if (mainController != null) {
            System.out.println("[DEBUG] Navigating to Address Booking Details page.");
            mainController.loadView("/project/demo/FXMLBookingPage/AddressBookingDetails.fxml");
        } else {
            System.err.println("[ERROR] Main controller is not set!");
        }
    }

}
