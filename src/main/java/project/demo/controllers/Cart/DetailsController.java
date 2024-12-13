package project.demo.controllers.Cart;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    private final DatabaseConfig db = new DatabaseConfig();

    public void setMainController(CartPageController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        loadAddresses(); // Load addresses into the grid
    }

    private void loadAddresses() {
        List<Address> addresses = fetchAddressesFromDatabase();

        addressGridPane.getChildren().clear();

        // Create a shared ToggleGroup
        ToggleGroup toggleGroup = new ToggleGroup();

        for (int i = 0; i < addresses.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLCartPage/CartAddressRow.fxml"));
                AnchorPane row = loader.load();

                CartAddressRowController controller = loader.getController();
                controller.setAddress(addresses.get(i));

                // Set the shared ToggleGroup for exclusive selection
                controller.setToggleGroup(toggleGroup);

                // Pass TextFields directly for address population
                controller.setFields(addressField, cityField, postalCodeField, provinceField, regionField);

                // Set the correct row index for each address
                GridPane.setRowIndex(row, i);
                addressGridPane.add(row, 0, i);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("[ERROR] Failed to load address row: " + e.getMessage());
            }
        }
    }

    private List<Address> fetchAddressesFromDatabase() {
        List<Address> addresses = new ArrayList<>();
        String query = "SELECT * FROM addresses WHERE user_id = ?";

        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Fetch the user ID from the session
            int userId = UserSession.getInstance().getUserId();
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

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
            System.err.println("[ERROR] Failed to fetch addresses for user ID: " + e.getMessage());
            e.printStackTrace();
        }

        return addresses;
    }

    public void goToShipping(ActionEvent actionEvent) {
        if (mainController != null) {
            mainController.loadView("/project/demo/FXMLCartPage/Shipping.fxml");
        } else {
            System.err.println("Main controller is not set!");
        }
    }

    public void goToCart(ActionEvent actionEvent) {
        if (mainController != null) {
            mainController.loadView("/project/demo/FXMLCartPage/CartTable.fxml");
        } else {
            System.err.println("Main controller is not set!");
        }
    }
}
