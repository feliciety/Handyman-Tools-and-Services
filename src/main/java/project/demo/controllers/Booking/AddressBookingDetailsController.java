package project.demo.controllers.Booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import project.demo.DataBase.DatabaseConfig;
import project.demo.models.Address;
import project.demo.models.UserSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AddressBookingDetailsController {

    private BookingPageController mainController; // Reference to the main controller

    @FXML
    private GridPane addressGridPane;

    @FXML
    private TextField addressField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField provinceField;
    @FXML
    private TextField regionField;
    @FXML
    private TextField bookingNoteField; // Shipping note equivalent for bookings


    private static Address chosenAddress;
    private static String bookingNote = "";
    private ToggleGroup addressToggleGroup = new ToggleGroup(); // Shared toggle group for radio buttons
    private final DatabaseConfig db = new DatabaseConfig();

    public static String getBookingNote() {
        return bookingNote;
    }

    public static Address getChosenAddress() {
        if (chosenAddress == null) {
            return new Address(0, "Default", "No Street", "No City", "0000", "No Province", "No Region");
        }
        return chosenAddress;
    }

    public void setMainController(BookingPageController mainController) {
        this.mainController = mainController;
        System.out.println("MainController set: " + mainController);
    }


    @FXML
    public void initialize() {
        if (addressGridPane == null) {
            System.err.println("[ERROR] addressGridPane is not initialized. Check FXML bindings.");
            return;
        }
        loadAddresses();
    }

    /**
     * Loads the addresses from the database and populates the grid pane.
     */
    private void loadAddresses() {
        int currentUserId = UserSession.getInstance().getUserId(); // Fetch current user ID
        List<Address> addresses = fetchAddressesFromDatabase(currentUserId);
        addressGridPane.getChildren().clear();

        for (int i = 0; i < addresses.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLBookingPage/BookAddressRow.fxml"));
                AnchorPane row = loader.load();

                BookingAddressRowController controller = loader.getController();
                controller.setAddress(addresses.get(i));
                controller.setToggleGroup(addressToggleGroup);

                RadioButton radioButton = controller.getRadioButton();
                int finalI = i;
                radioButton.setOnAction(event -> {
                    chosenAddress = addresses.get(finalI);
                    populateFields();
                });

                if (i == 0) {
                    radioButton.setSelected(true);
                    chosenAddress = addresses.get(0);
                    populateFields();
                }

                GridPane.setRowIndex(row, i);
                addressGridPane.add(row, 0, i);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("[ERROR] Failed to load address row.");
            }
        }
    }

    /**
     * Fetches addresses from the database for the current logged-in user.
     */
    private List<Address> fetchAddressesFromDatabase(int userId) {
        List<Address> addresses = new ArrayList<>();
        String query = "SELECT * FROM addresses WHERE user_id = ?";

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                addresses.add(new Address(
                        resultSet.getInt("id"),
                        resultSet.getString("address_type"),
                        resultSet.getString("street"),
                        resultSet.getString("city"),
                        resultSet.getString("postal_code"),
                        resultSet.getString("province"),
                        resultSet.getString("region")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addresses;
    }


    private void populateFields() {
        addressField.setText(chosenAddress.getStreet());
        cityField.setText(chosenAddress.getCity());
        postalCodeField.setText(chosenAddress.getPostalCode());
        provinceField.setText(chosenAddress.getProvince());
        regionField.setText(chosenAddress.getRegion());
    }

    @FXML
    public void goToBookingCartTable(ActionEvent actionEvent) {
        mainController.goToBookingCartTable();
    }

    @FXML
    public void goToPayment(ActionEvent actionEvent) {
            if (mainController == null) {
                System.err.println("MainController is null in goToPayment!");
                return;
            }
            System.out.println("Navigating to payment...");
            mainController.goToPayment();
        }
    }

