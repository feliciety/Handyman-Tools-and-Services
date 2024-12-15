package project.demo.controllers.Booking;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import project.demo.models.Address;

public class BookingAddressRowController {

    @FXML
    private Label streetLabel; // Display the street of the address

    @FXML
    private Label cityLabel; // Display the city of the address

    @FXML
    private Label postalCodeLabel; // Display the postal code

    @FXML
    private Label provinceLabel; // Display the province

    @FXML
    private Label regionLabel; // Display the region

    @FXML
    private RadioButton selectRadioButton; // Allow user to select this address

    private Address address; // Store the current address data

    /**
     * Sets the address for this row and populates the UI elements.
     *
     * @param address The address data to display.
     */
    public void setAddress(Address address) {
        this.address = address;

        // Populate UI elements with address details
        streetLabel.setText(address.getStreet());
        cityLabel.setText(address.getCity());
        postalCodeLabel.setText(address.getPostalCode());
        provinceLabel.setText(address.getProvince());
        regionLabel.setText(address.getRegion());
    }

    /**
     * Sets the toggle group for the radio button. This ensures only one radio button
     * can be selected at a time in the parent container.
     *
     * @param toggleGroup The ToggleGroup to assign.
     */
    public void setToggleGroup(ToggleGroup toggleGroup) {
        selectRadioButton.setToggleGroup(toggleGroup);
    }

    /**
     * Gets the RadioButton instance for selecting this address.
     *
     * @return The RadioButton instance.
     */
    public RadioButton getRadioButton() {
        return selectRadioButton;
    }

    /**
     * Gets the selected address displayed in this row.
     *
     * @return The Address instance.
     */
    public Address getAddress() {
        return address;
    }
}