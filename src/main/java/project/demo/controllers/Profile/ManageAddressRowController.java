package project.demo.controllers.Profile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import project.demo.dao.AddressDAO;
import project.demo.dao.AddressDAOImpl;
import project.demo.controllers.Profile.AddEditAddressController;
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

    /**
     * Sets the address data for this row and updates the UI labels.
     *
     * @param address The address to display.
     */
    public void setAddress(Address address) {
        this.address = address;

        // Populate the labels with the address details
        AddressTyepeLabel.setText(address.getType());
        StreetCityPostalCodeLabel.setText(address.getStreet() + ", " + address.getCity() + ", " + address.getPostalCode());
        ProvinceRegionLabel.setText(address.getProvince() + ", " + address.getRegion()); // Include region here
    }


    /**
     * Handles the Edit button click.
     */
    @FXML
    public void onEditClicked(ActionEvent actionEvent) {
        if (address == null) {
            System.err.println("[ERROR] No address is set for this row.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLProfilePage/AddEditAddressForm.fxml"));
            AnchorPane popupContent = loader.load();

            AddEditAddressController controller = loader.getController();
            controller.setAddress(address); // Pass the current address to the form

            // Create a new stage for the popup
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Edit Address");
            popupStage.setScene(new Scene(popupContent));
            popupStage.showAndWait(); // Wait for popup to close

            // Automatically update the labels after editing
            setAddress(controller.getUpdatedAddress());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to load AddEditAddressForm.fxml: " + e.getMessage());
        }
    }

    /**
     * Handles the Delete button click.
     */
    @FXML
    public void onDeleteClicked(ActionEvent actionEvent) {
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
                // Delete the address from the database
                boolean success = addressDAO.deleteAddress(address.getId());
                if (success) {
                    System.out.println("[INFO] Address deleted successfully.");
                    removeRowFromGrid(); // Remove this row from the grid
                } else {
                    System.err.println("[ERROR] Failed to delete address.");
                }
            }
        });
    }

    /**
     * Removes this row from the GridPane.
     */
    private void removeRowFromGrid() {
        HBox parentRow = (HBox) AddressTyepeLabel.getParent();
        GridPane gridPane = (GridPane) parentRow.getParent();
        gridPane.getChildren().remove(parentRow);
    }
}
