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
        // Set up table columns
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

        // Listen for changes in bookedItems (e.g., when an item is removed)
        bookedItems.addListener((observable, oldValue, newValue) -> bookingTable.refresh());
    }

    // Centers the content of a TableColumn with generic content
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

    // Centers the content of a TableColumn with HBox content
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

    // Centers the content of a TableColumn with Button content
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

    // Parses price range from the service price string
    private void parsePriceRange(BookServiceItem item, String priceRange) {
        double minPrice = 0.0;
        double maxPrice = 0.0;
        double midPrice = 0.0;

        if (priceRange.contains(" - ")) {
            String[] prices = priceRange.split(" - ");
            try {
                minPrice = Double.parseDouble(prices[0].trim());
                maxPrice = Double.parseDouble(prices[1].trim());
                midPrice = (minPrice + maxPrice) / 2;
            } catch (NumberFormatException e) {
                System.err.println("[ERROR] Failed to parse price range: " + priceRange);
                e.printStackTrace();
            }
        } else {
            try {
                minPrice = Double.parseDouble(priceRange.trim());
                maxPrice = minPrice;
                midPrice = minPrice;
            } catch (NumberFormatException e) {
                System.err.println("[ERROR] Failed to parse single price: " + priceRange);
                e.printStackTrace();
            }
        }

        // Set the parsed prices and mid price to the item
        item.setMinPrice(minPrice);
        item.setMaxPrice(maxPrice);
        item.setMidPrice(midPrice);
    }

    // Removes a booked service item from the table
    public void removeBookedService(BookServiceItem serviceItem) {
        bookedItems.remove(serviceItem);
        updateTable();
    }

    // Refresh the table to reflect updates
    public void updateTable() {
        bookingTable.refresh();
    }

    // Adds a service to the booking table
    public void addService(BookServiceItem serviceItem) {
        // Parse the price range before adding to the cart
        parsePriceRange(serviceItem, serviceItem.getPriceRange());

        BookServiceManager.getInstance().addService(serviceItem);
        updateTable();
    }

    // Set reference to the main controller
    public void setMainController(BookingPageController mainController) {
        this.mainController = mainController;
    }

    // Navigates to the Services page
    public void goToServices(ActionEvent actionEvent) {
        if (mainController != null) {
            mainController.loadView("/project/demo/FXMLBookingPage/BookingCartTable.fxml");
        }
    }

    // Navigates to the Address Booking Details page
    public void goToAddressBookingDetails(ActionEvent actionEvent) {
        if (mainController != null) {
            mainController.loadView("/project/demo/FXMLBookingPage/AddressBookingDetails.fxml");
        }
    }
}
