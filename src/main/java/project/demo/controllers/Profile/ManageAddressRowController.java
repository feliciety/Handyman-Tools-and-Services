package project.demo.controllers.Profile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.demo.dao.AddressDAO;
import project.demo.dao.AddressDAOImpl;
import project.demo.models.Address;

import java.io.IOException;

public class ManageAddressRowController {

    @FXML
    private Label AddressTyepeLabel;

    @FXML
    private Label StreetCityPostalCodeLabel;

    @FXML
    private Label ProvinceRegionLabel;

    private Address address; // Holds the address data for this row
    private AddressDAO addressDAO = new AddressDAOImpl(); // DAO for database operations
    private ManageAddressesController parentController; // Reference to the parent controller for grid updates

    /**
     * Sets the address data for this row and updates the UI labels.
     *
     * @param address The address to display.
     * @param parentController The parent controller to handle grid updates.
     */
    public void setAddress(Address address, ManageAddressesController parentController) {
        this.address = address;
        this.parentController = parentController;

        if (address != null) {
            // Populate the labels with the address details
            AddressTyepeLabel.setText(address.getType());
            StreetCityPostalCodeLabel.setText(address.getStreet() + ", " + address.getCity() + ", " + address.getPostalCode());
            ProvinceRegionLabel.setText(address.getProvince() + ", " + address.getRegion());
        } else {
            System.err.println("[ERROR] Address data is null.");
        }
    }

    /**
     * Opens the Edit Address Form and updates the row data if the user saves changes.
     */
    public void onEditClicked(javafx.event.ActionEvent actionEvent) {
        if (address == null) {
            System.err.println("[ERROR] No address is set for this row.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLProfilePage/EditAddressForm.fxml"));
            AnchorPane popupContent = loader.load();

            EditAddressFormController controller = loader.getController();
            controller.setAddress(address);

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Edit Address");
            popupStage.setScene(new Scene(popupContent));
            popupStage.showAndWait();

            Address updatedAddress = controller.getUpdatedAddress();
            if (updatedAddress != null) {
                setAddress(updatedAddress, parentController);
                System.out.println("[INFO] Address updated successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to load EditAddressForm.fxml: " + e.getMessage());
        }
    }

    /**
     * Deletes the address and removes the row from the grid.
     */
    public void onDeleteClicked(javafx.event.ActionEvent actionEvent) {
        if (address == null) {
            System.err.println("[ERROR] No address is set for this row.");
            return;
        }

        // Confirm deletion with the user
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Are you sure you want to delete this address?");
        confirmationAlert.setContentText("This action cannot be undone.");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response.getButtonData().isDefaultButton()) {
                // Attempt to delete the address from the database
                boolean success = addressDAO.deleteAddress(address.getId());
                if (success) {
                    System.out.println("[INFO] Address deleted successfully.");
                    // Remove the row from the grid and update the grid
                    parentController.removeAddressFromGrid(address);
                } else {
                    System.err.println("[ERROR] Failed to delete address from the database.");
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Deletion Failed");
                    errorAlert.setHeaderText("Address deletion failed.");
                    errorAlert.setContentText("An error occurred while attempting to delete the address. Please try again.");
                    errorAlert.showAndWait();
                }
            }
        });
    }
}
