package project.demo.controllers.Cart;

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

public class DetailsController {

    private CartPageController mainController; // Reference to the main controller

    @FXML private GridPane addressGridPane;

    @FXML private TextField addressField;
    @FXML private TextField cityField;
    @FXML private TextField postalCodeField;
    @FXML private TextField provinceField;
    @FXML private TextField regionField;
    @FXML private TextField shippingNoteField;

    private static Address chosenAddress;
    private static String shippingNote = "";

    private ToggleGroup addressToggleGroup = new ToggleGroup();
    private final DatabaseConfig db = new DatabaseConfig();

    public static String getShippingNote() {
        return shippingNote;
    }

    public static Address getChosenAddress() {
        if (chosenAddress == null) {
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
     * Loads the addresses for the current logged-in user from the database and populates the grid pane.
     */
    private void loadAddresses() {
        int currentUserId = UserSession.getInstance().getUserId(); // Get current user ID
        List<Address> addresses = fetchAddressesFromDatabase(currentUserId); // Pass currentUserId
        addressGridPane.getChildren().clear();

        for (int i = 0; i < addresses.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLCartPage/CartAddressRow.fxml"));
                AnchorPane row = loader.load();

                CartAddressRowController controller = loader.getController();
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
            }
        }
    }

    /**
     * Fetches addresses from the database for the current logged-in user.
     *
     * @param userId The ID of the logged-in user.
     * @return List of Address objects
     */
    private List<Address> fetchAddressesFromDatabase(int userId) {
        List<Address> addresses = new ArrayList<>();
        String query = "SELECT * FROM addresses WHERE user_id = ?";

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId); // Use current user ID from UserSession
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
    private AnchorPane contentPane;

    @FXML
    private void goToShipping() {
        mainController.goToShipping();
    }

    @FXML
    public void goToCart(ActionEvent actionEvent) {
        mainController.goToCart();
    }

}
