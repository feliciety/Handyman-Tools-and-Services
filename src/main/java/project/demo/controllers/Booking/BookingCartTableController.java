package project.demo.controllers.Booking;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import project.demo.controllers.Main.MainStructureController;
import project.demo.models.BookServiceItem;
import project.demo.models.BookServiceManager;

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
    private TableColumn<BookServiceItem, String> serviceFeeCol;

    @FXML
    private TableColumn<BookServiceItem, LocalDate> bookingDateCol;

    @FXML
    private TableColumn<BookServiceItem, Button> deleteButtonCol;

    private final ObservableList<BookServiceItem> bookedItems = BookServiceManager.getInstance().getBookedServices();
    private BookingPageController mainController;
    private MainStructureController mainStructureController; // Reference for main structure navigation

    public void setMainStructureController(MainStructureController mainStructureController) {
        this.mainStructureController = mainStructureController;
        if (this.mainStructureController != null) {
            System.out.println("[DEBUG] MainStructureController has been properly set in CartTableController.");
        } else {
            System.out.println("[ERROR] Failed to set MainStructureController in CartTableController.");
        }
    }

    @FXML
    public void initialize() {
        // Set up table columns
        serviceImageCol.setCellValueFactory(new PropertyValueFactory<>("serviceImageView"));
        serviceNameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        jobComplexityCol.setCellValueFactory(new PropertyValueFactory<>("jobComplexityControl"));
        serviceFeeCol.setCellValueFactory(cellData -> cellData.getValue().formattedServiceFeeProperty());
        deleteButtonCol.setCellFactory(tc -> new TableCell<>() {
            private final Button removeButton = new Button("X");

            {
                removeButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                removeButton.setOnAction(event -> {
                    BookServiceItem item = getTableView().getItems().get(getIndex());
                    if (item != null) {
                        BookServiceManager.getInstance().removeService(item); // Remove from manager
                        bookingTable.refresh();
                    }
                });
            }

            @Override
            protected void updateItem(Button button, boolean empty) {
                super.updateItem(button, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removeButton);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        // Custom bookingDate column with DatePicker
        bookingDateCol.setCellFactory(column -> new TableCell<>() {
            private final DatePicker datePicker = new DatePicker();

            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (empty || getIndex() < 0 || getIndex() >= bookedItems.size()) {
                    setGraphic(null);
                } else {
                    BookServiceItem serviceItem = getTableView().getItems().get(getIndex());

                    // Set the DatePicker value
                    datePicker.setValue(serviceItem.bookingDateProperty().get());
                    datePicker.setOnAction(event -> {
                        serviceItem.setBookingDate(datePicker.getValue().toString());
                        bookingTable.refresh();
                    });

                    setGraphic(datePicker);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        // Set table data
        bookingTable.setItems(bookedItems);

        // Listen for changes in bookedItems
        bookedItems.addListener((ListChangeListener<? super BookServiceItem>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    bookingTable.refresh();
                }
            }
        });

        // Center column content
        centerColumnContent(serviceImageCol);
        centerColumnContent(serviceNameCol);
        centerHBoxContent(jobComplexityCol);
        centerColumnContent(serviceFeeCol);
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
                } else {
                    setText(item.toString());
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
                    setGraphic(null);
                } else {
                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);
                }
            }
        });
    }



    public void goToServices(ActionEvent actionEvent) {
        if (mainStructureController != null) {
            mainStructureController.navigateTo("/project/demo/FXMLServicePage/ServicePage.fxml");
        } else {
            System.err.println("[ERROR] MainStructureController is not set in BookingCartTableController.");
        }
    }

    public void goToAddressBookingDetails(ActionEvent actionEvent) {
        mainController.goToAddressBookingDetails();
    }

    public void setMainController(BookingPageController mainController) {
        this.mainController = mainController;
    }
}
