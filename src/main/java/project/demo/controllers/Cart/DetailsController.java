package project.demo.controllers.Cart;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import project.demo.DataBase.DatabaseConfig;
import project.demo.models.Address;
import project.demo.models.UserSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DetailsController {


    private CartPageController mainController; // Reference to the main controller

    @FXML
    private GridPane addressGridPane;

    @FXML
    private TextField addressField; // Address input field

    @FXML
    private TextField cityField; // City input field

    @FXML
    private TextField postalCodeField; // Postal Code input field

    @FXML
    private TextField provinceField; // Province input field

    @FXML
    private TextField regionField; // Region input field

    @FXML
    private TextField shippingNoteField; // Shipping note input field

    private static Address chosenAddress;
    private static String shippingNote = "";
    private ToggleGroup addressToggleGroup = new ToggleGroup();

    private final DatabaseConfig db = new DatabaseConfig();
    private Address address;

    public static String getShippingNote() {
        return shippingNote;
    }

    // Method to get the chosen address

    public static Address getChosenAddress() {
        if (chosenAddress == null) {
            System.err.println("[ERROR] No address chosen. Returning default address.");
            return new Address(0, "Default", "No Street", "No City", "0000", "No Province", "No Region");
        }
        return chosenAddress;
    }


    public void setMainController(CartPageController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        if (addressGridPane == null) {
            System.err.println("[ERROR] addressGridPane is not initialized. Check FXML mappings.");
            return;
        }
        loadAddresses();
    }

    /**
     * Loads the addresses from the database and populates the address grid pane.
     */
    private void loadAddresses() {
        List<Address> addresses = fetchAddressesFromDatabase();
        addressGridPane.getChildren().clear();

        for (int i = 0; i < addresses.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLCartPage/CartAddressRow.fxml"));
                AnchorPane row = loader.load();

                // Set the address to the row controller
                CartAddressRowController controller = loader.getController();
                controller.setAddress(addresses.get(i));
                controller.setToggleGroup(addressToggleGroup);

                // Handle selection
                RadioButton radioButton = controller.getRadioButton();
                int finalI = i;
                radioButton.setOnAction(event -> {
                    chosenAddress = addresses.get(finalI);
                    address = chosenAddress; // Set the address object
                    populateFields(); // Populate the text fields with the selected address
                    System.out.println("[INFO] Selected Address: " + chosenAddress.getFullAddress());
                });

                // Select the first address by default and populate the fields
                if (i == 0) {
                    radioButton.setSelected(true);
                    chosenAddress = addresses.get(0);
                    address = chosenAddress; // Set the address object
                    populateFields(); // Populate the fields for the first address
                }

                GridPane.setRowIndex(row, i);
                addressGridPane.add(row, 0, i);

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("[ERROR] Failed to load address row: " + e.getMessage());
            }
        }
    }


    /**
     * Fetches addresses from the database for the logged-in user.
     *
     * @return List of Address objects
     */
    private List<Address> fetchAddressesFromDatabase() {
        List<Address> addresses = new ArrayList<>();
        String query = "SELECT * FROM addresses WHERE user_id = ?";

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, 1); // Assuming a static user ID for testing
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Address address = new Address(
                        resultSet.getInt("id"),
                        resultSet.getString("address_type"),
                        resultSet.getString("street"),
                        resultSet.getString("city"),
                        resultSet.getString("postal_code"),
                        resultSet.getString("province"),
                        resultSet.getString("region")
                );
                addresses.add(address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addresses;
    }



    /**
     * Saves the manually entered shipping note and address fields.
     */
    @FXML
    public void saveDetails() {
        chosenAddress = new Address(
                0, // Temporary ID for new entries
                "Custom",
                addressField.getText(),
                cityField.getText(),
                postalCodeField.getText(),
                provinceField.getText(),
                regionField.getText()
        );
        shippingNote = shippingNoteField.getText();
        System.out.println("[INFO] Custom address saved: " + chosenAddress.getFullAddress());
    }
    @FXML
    private AnchorPane contentPane;

    @FXML
    private void goToShipping() {
        mainController.goToShipping();
    }

    @FXML
    public void goToCart(ActionEvent actionEvent) {
        mainController.goToCart();
    }

    private void populateFields() {
        addressField.setText(address.getStreet());
        cityField.setText(address.getCity());
        postalCodeField.setText(address.getPostalCode());
        provinceField.setText(address.getProvince());
        regionField.setText(address.getRegion());
    }
}
