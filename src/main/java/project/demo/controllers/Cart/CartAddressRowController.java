package project.demo.controllers.Cart;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import project.demo.models.Address;

public class CartAddressRowController {

    @FXML
    private RadioButton selectAddressRadioButton;

    @FXML
    private Label AddressTypeLabel;

    @FXML
    private Label StreetCityPostalCodeLabel;

    @FXML
    private Label ProvinceRegionLabel;

    private Address address;
    private TextField addressField, cityField, postalCodeField, shippingNoteField;
    private TextField provinceField, regionField;

    public void setAddress(Address address) {
        this.address = address;
        AddressTypeLabel.setText(address.getType());
        StreetCityPostalCodeLabel.setText(address.getStreet() + ", " + address.getCity() + ", " + address.getPostalCode());
        ProvinceRegionLabel.setText(address.getProvince() + ", " + address.getRegion());
    }

    public void setFields(TextField addressField, TextField cityField, TextField postalCodeField,
                          TextField provinceField, TextField regionField) {
        this.addressField = addressField;
        this.cityField = cityField;
        this.postalCodeField = postalCodeField;
        this.provinceField = provinceField;
        this.regionField = regionField;

        selectAddressRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateFields();
            }
        });
    }

    private void populateFields() {
        addressField.setText(address.getStreet());
        cityField.setText(address.getCity());
        postalCodeField.setText(address.getPostalCode());
        provinceField.setText(address.getProvince());
        regionField.setText(address.getRegion());
        shippingNoteField.setText(""); // Clear or set default shipping notes
    }
}
