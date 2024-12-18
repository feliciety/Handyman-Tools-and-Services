package project.demo.controllers.Booking;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import project.demo.models.Address;

public class BookingAddressRowController {

    @FXML   public Label StreetCityPostalCodeLabel;
    @FXML  public Label ProvinceRegionLabel;
    @FXML  public Label AddressTyepeLabel;
    @FXML  public RadioButton selectAddressRadioButton;


    private Address address; // Store the current address data

    private TextField addressField, cityField, postalCodeField;
    private TextField provinceField, regionField;

    public void setAddress(Address address) {
        this.address = address;
        AddressTyepeLabel.setText(address.getType());
        StreetCityPostalCodeLabel.setText(address.getStreet() + ", " + address.getCity() + ", " + address.getPostalCode());
        ProvinceRegionLabel.setText(address.getProvince() + ", " + address.getRegion());
        selectAddressRadioButton.setUserData(address); // Attach Address object
    }

    public void setToggleGroup(ToggleGroup group) {
        selectAddressRadioButton.setToggleGroup(group);
    }

    public RadioButton getRadioButton() {
        return selectAddressRadioButton;
    }

    // Set fields for address population
    public void setFields(TextField addressField, TextField cityField, TextField postalCodeField,
                          TextField provinceField, TextField regionField) {
        this.addressField = addressField;
        this.cityField = cityField;
        this.postalCodeField = postalCodeField;
        this.provinceField = provinceField;
        this.regionField = regionField;

        // Add listener for when the radio button is selected
        selectAddressRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateFields();
            }
        });
    }

    // Populate fields with the selected address details
    private void populateFields() {
        addressField.setText(address.getStreet());
        cityField.setText(address.getCity());
        postalCodeField.setText(address.getPostalCode());
        provinceField.setText(address.getProvince());
        regionField.setText(address.getRegion());
    }


}