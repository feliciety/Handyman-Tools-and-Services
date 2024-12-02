package project.demo.controllers.Profile;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.demo.dao.AddressDAO;
import project.demo.dao.AddressDAOImpl;
import project.demo.models.Address;
import project.demo.models.UserSession;

import java.util.stream.Collectors;

import static project.demo.controllers.Profile.EditAddressFormController.getAddress;

public class AddAddressFormController {

    @FXML
    private RadioButton workRBTN;

    @FXML
    private RadioButton homeRBTN;

    @FXML
    private TextField streetField;

    @FXML
    private TextField barangayField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private ComboBox<String> provinceComboBox;

    @FXML
    private ComboBox<String> regionComboBox;

    private Address addressToEdit;
    private final AddressDAO addressDAO = new AddressDAOImpl();

    private final ObservableList<String> provinces = FXCollections.observableArrayList(
            "Abra", "Agusan del Norte", "Agusan del Sur", "Aklan", "Albay", "Antique",
            "Apayao", "Aurora", "Basilan", "Bataan", "Batanes", "Batangas",
            "Benguet", "Biliran", "Bohol", "Bukidnon", "Bulacan", "Cagayan",
            "Camarines Norte", "Camarines Sur", "Camiguin", "Capiz", "Catanduanes",
            "Cavite", "Cebu", "Cotabato", "Davao de Oro", "Davao del Norte",
            "Davao del Sur", "Davao Occidental", "Davao Oriental", "Dinagat Islands",
            "Eastern Samar", "Guimaras", "Ifugao", "Ilocos Norte", "Ilocos Sur",
            "Iloilo", "Isabela", "Kalinga", "La Union", "Laguna", "Lanao del Norte",
            "Lanao del Sur", "Leyte", "Maguindanao del Norte", "Maguindanao del Sur",
            "Marinduque", "Masbate", "Metro Manila", "Misamis Occidental",
            "Misamis Oriental", "Mountain Province", "Negros Occidental",
            "Negros Oriental", "Northern Samar", "Nueva Ecija", "Nueva Vizcaya",
            "Occidental Mindoro", "Oriental Mindoro", "Palawan", "Pampanga",
            "Pangasinan", "Quezon", "Quirino", "Rizal", "Romblon", "Samar",
            "Sarangani", "Siquijor", "Sorsogon", "South Cotabato",
            "Southern Leyte", "Sultan Kudarat", "Sulu", "Surigao del Norte",
            "Surigao del Sur", "Tarlac", "Tawi-Tawi", "Zambales",
            "Zamboanga del Norte", "Zamboanga del Sur", "Zamboanga Sibugay");

    private final ObservableList<String> regions = FXCollections.observableArrayList(
            "Region I - Ilocos Region",
            "Region II - Cagayan Valley",
            "Region III - Central Luzon",
            "Region IV-A - CALABARZON",
            "Region IV-B - MIMAROPA",
            "Region V - Bicol Region",
            "Region VI - Western Visayas",
            "Region VII - Central Visayas",
            "Region VIII - Eastern Visayas",
            "Region IX - Zamboanga Peninsula",
            "Region X - Northern Mindanao",
            "Region XI - Davao Region",
            "Region XII - SOCCSKSARGEN",
            "Region XIII - Caraga",
            "BARMM - Bangsamoro Autonomous Region in Muslim Mindanao",
            "NCR - National Capital Region",
            "CAR - Cordillera Administrative Region"
    );

    @FXML
    public void initialize() {
        // Initialize combo boxes
        provinceComboBox.setItems(provinces);
        regionComboBox.setItems(regions);

        // Enable search filtering
        setupSearchFilter(provinceComboBox, provinces);
        setupSearchFilter(regionComboBox, regions);
    }

    private void setupSearchFilter(ComboBox<String> comboBox, ObservableList<String> items) {
        comboBox.setEditable(true);
        comboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<String> filteredItems = FXCollections.observableArrayList(
                    items.stream()
                            .filter(item -> item.toLowerCase().contains(newValue.toLowerCase()))
                            .collect(Collectors.toList())
            );

            comboBox.setItems(filteredItems);
            comboBox.getEditor().setText(newValue);

            if (!comboBox.isShowing()) {
                comboBox.show();
            }
        });

        comboBox.setOnAction(event -> {
            if (comboBox.getValue() != null) {
                comboBox.getEditor().setText(comboBox.getValue());
            }
        });
    }

    public void setAddressToEdit(Address address) {
        this.addressToEdit = address;

        if (address != null) {
            streetField.setText(address.getStreet());
            barangayField.setText(address.getBarangay());
            cityField.setText(address.getCity());
            postalCodeField.setText(address.getPostalCode());
            provinceComboBox.setValue(address.getProvince());
            regionComboBox.setValue(address.getRegion());

            if ("Work".equalsIgnoreCase(address.getType())) {
                workRBTN.setSelected(true);
            } else {
                homeRBTN.setSelected(true);
            }
        }
    }

    @FXML
    public void onSave() {
        if (streetField.getText().isEmpty() || cityField.getText().isEmpty() ||
                provinceComboBox.getValue() == null || regionComboBox.getValue() == null) {
            System.err.println("[ERROR] All fields must be filled.");
            return;
        }

        if (addressToEdit == null) {
            addressToEdit = new Address();
        }

        // Set address details
        addressToEdit.setStreet(streetField.getText());
        addressToEdit.setBarangay(barangayField.getText());
        addressToEdit.setCity(cityField.getText());
        addressToEdit.setPostalCode(postalCodeField.getText());
        addressToEdit.setProvince(provinceComboBox.getValue());
        addressToEdit.setRegion(regionComboBox.getValue()); // Ensure region is being set
        addressToEdit.setType(workRBTN.isSelected() ? "Work" : "Home");
        addressToEdit.setUserId(UserSession.getInstance().getUserId());

        // Save or update the address in the database
        addressDAO.saveOrUpdateAddress(addressToEdit);

        // Close the popup
        Stage stage = (Stage) streetField.getScene().getWindow();
        stage.close();
    }


    @FXML
    public void onCancel() {
        Stage stage = (Stage) streetField.getScene().getWindow();
        stage.close();
    }

    public void setAddress(Address address) {
        this.addressToEdit = address;

        if (address != null) {
            // Pre-fill form fields with existing address data
            AdressField(address, streetField, barangayField, cityField, postalCodeField, provinceComboBox, regionComboBox, workRBTN, homeRBTN);
        } else {
            // Clear form fields for a new address
            streetField.clear();
            barangayField.clear();
            cityField.clear();
            postalCodeField.clear();
            provinceComboBox.setValue(null);
            regionComboBox.setValue(null);
            workRBTN.setSelected(false);
            homeRBTN.setSelected(false);
        }
    }

    static void AdressField(Address address, TextField streetField, TextField barangayField, TextField cityField, TextField postalCodeField, ComboBox<String> provinceComboBox, ComboBox<String> regionComboBox, RadioButton workRBTN, RadioButton homeRBTN) {
        streetField.setText(address.getStreet());
        barangayField.setText(address.getBarangay());
        cityField.setText(address.getCity());
        postalCodeField.setText(address.getPostalCode());
        provinceComboBox.setValue(address.getProvince());
        regionComboBox.setValue(address.getRegion());
        if ("Work".equalsIgnoreCase(address.getType())) {
            workRBTN.setSelected(true);
        } else {
            homeRBTN.setSelected(true);
        }
    }

    public Address getUpdatedAddress() {
        // Validate fields before returning the address
        return getAddress(streetField, cityField, provinceComboBox, regionComboBox, barangayField, postalCodeField, workRBTN);
    }

}
