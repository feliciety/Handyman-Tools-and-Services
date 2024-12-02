package project.demo.controllers.Profile;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.demo.models.Address;
import project.demo.dao.AddressDAO;
import project.demo.dao.AddressDAOImpl;
import project.demo.models.UserSession;

import java.io.IOException;
import java.util.List;

public class ManageAddressesController {

    @FXML
    private GridPane addressGridPane; // The grid where address rows will be displayed

    private final AddressDAO addressDAO = new AddressDAOImpl();
    private final ObservableList<Address> addressList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        System.out.println("[DEBUG] ManageAddressesController initialized.");
        loadAddresses(); // Load addresses into the list
        refreshGrid(); // Refresh the grid immediately with the loaded addresses

        // Add a listener to refresh the grid whenever the list changes
        addressList.addListener((ListChangeListener<? super Address>) change -> refreshGrid());
    }

    /**
     * Loads addresses from the database and populates the ObservableList.
     */
    private void loadAddresses() {
        UserSession session = UserSession.getInstance();
        List<Address> addresses = addressDAO.getAddressesByUserId(session.getUserId());
        if (addresses != null) {
            System.out.println("[DEBUG] Loaded " + addresses.size() + " addresses for user.");
            addressList.setAll(addresses);
        } else {
            System.out.println("[DEBUG] No addresses found for user.");
        }
    }

    /**
     * Refreshes the address grid to reflect the ObservableList changes.
     */
    private void refreshGrid() {
        addressGridPane.getChildren().clear(); // Clear the grid

        for (int i = 0; i < addressList.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLProfilePage/ManageAddressRow.fxml"));
                Node row = loader.load();

                // Pass address data to the row's controller
                ManageAddressRowController controller = loader.getController();
                controller.setAddress(addressList.get(i)); // Pass address to the controller

                addressGridPane.addRow(i, row); // Add the row to the grid
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("[ERROR] Failed to load ManageAddressRow.fxml: " + e.getMessage());
            }
        }
    }

    /**
     * Handles the "Add New Address" button click.
     */
    @FXML
    public void addAddressButton(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLProfilePage/AddAddressForm.fxml"));
            AnchorPane popupContent = loader.load();

            AddAddressFormController controller = loader.getController();
            controller.setAddress(null); // Indicate we're adding a new address

            // Create a popup for adding a new address
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Add New Address");
            popupStage.setScene(new Scene(popupContent));
            popupStage.showAndWait(); // Wait until popup is closed

            loadAddresses(); // Reload addresses to reflect changes
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to load AddAddressForm.fxml: " + e.getMessage());
        }
    }
}
