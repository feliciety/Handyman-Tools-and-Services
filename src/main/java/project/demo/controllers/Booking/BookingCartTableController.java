package project.demo.controllers.Booking;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import project.demo.models.Service;
import javafx.event.ActionEvent;

import java.time.LocalDate;

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
    private TableColumn<BookServiceItem, LocalDate> bookingDateCol;

    @FXML
    private TableColumn<BookServiceItem, Button> deleteButtonCol;

    private final ObservableList<BookServiceItem> bookedItems = BookServiceManager.getInstance().getBookedServices();

    private BookingPageController mainController;

    @FXML
    public void initialize() {
        // Set up table columns
        serviceImageCol.setCellValueFactory(new PropertyValueFactory<>("serviceImageView"));
        serviceNameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        jobComplexityCol.setCellValueFactory(new PropertyValueFactory<>("jobComplexityControl"));
        serviceFeeCol.setCellValueFactory(new PropertyValueFactory<>("serviceFee"));
        deleteButtonCol.setCellValueFactory(new PropertyValueFactory<>("removeButton"));

        // Custom bookingDate column to use DatePicker
        bookingDateCol.setCellFactory(column -> new TableCell<>() {
            private final DatePicker datePicker = new DatePicker();

            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (empty || getIndex() < 0 || getIndex() >= bookedItems.size()) {
                    setGraphic(null);
                } else {
                    BookServiceItem serviceItem = getTableView().getItems().get(getIndex());

                    // Set the value and listen for updates
                    datePicker.setValue(serviceItem.bookingDateProperty().get());
                    datePicker.setOnAction(event -> {
                        serviceItem.setBookingDate(datePicker.getValue().toString());
                        System.out.println("Updated booking date: " + datePicker.getValue());
                        getTableView().refresh(); // Refresh table view after update
                    });

                    setGraphic(datePicker);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        // Center align all other columns
        centerColumnContent(serviceImageCol);
        centerColumnContent(serviceNameCol);
        centerHBoxContent(jobComplexityCol);
        centerColumnContent(serviceFeeCol);
        centerButtonContent(deleteButtonCol);

        // Bind the bookedItems from BookServiceManager to the table
        bookingTable.setItems(bookedItems);

        // Listen for changes in bookedItems (e.g., when an item is removed)
        bookedItems.addListener((ListChangeListener<? super BookServiceItem>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    bookingTable.refresh();
                }
            }
        });
    }

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
                setAlignment(Pos.CENTER);
            }
        });
    }

    private void centerHBoxContent(TableColumn<BookServiceItem, HBox> column) {
        column.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(HBox hbox, boolean empty) {
                super.updateItem(hbox, empty);
                if (empty || hbox == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);
                    setText(null);
                }
                setAlignment(Pos.CENTER);
            }
        });
    }

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
                    button.setAlignment(Pos.CENTER);
                    setText(null);
                }
                setAlignment(Pos.CENTER);
            }
        });
    }

    public void addService(Service service, String jobComplexity, String bookingDate) {
        // Create a new BookServiceItem
        BookServiceItem serviceItem = new BookServiceItem(service, jobComplexity, bookingDate);

        // Add the service item to the booking manager
        BookServiceManager.getInstance().addService(serviceItem);

        // Refresh the table to reflect the updated items
        updateTable();
    }

    public void removeBookedService(BookServiceItem serviceItem) {
        bookedItems.remove(serviceItem);
        updateTable();
    }

    public void updateTable() {
        bookingTable.refresh();
    }

    public void setMainController(BookingPageController mainController) {
        this.mainController = mainController;
    }

    public void goToServices(ActionEvent actionEvent) {
        if (mainController != null) {
            mainController.loadView("/project/demo/FXMLBookingPage/BookingCartTable.fxml");
        }
    }

    public void goToAddressBookingDetails(ActionEvent actionEvent) {
        if (mainController != null) {
            mainController.loadView("/project/demo/FXMLBookingPage/AddressBookingDetails.fxml");
        }
    }
}
