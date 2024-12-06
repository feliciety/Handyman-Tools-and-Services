package project.demo.controllers.Profile;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;
import project.demo.dao.AddressDAO;
import project.demo.dao.AddressDAOImpl;
import project.demo.models.Address;
import project.demo.models.UserSession;

public class EditAddressFormController {

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
    private AddressDAO addressDAO = new AddressDAOImpl(); // Initialize DAO instance

    private final ObservableList<String> provinces = FXCollections.observableArrayList(
            "Abra", "Agusan del Norte", "Agusan del Sur", "Aklan", "Albay", "Antique",
            "Apayao", "Aurora", "Basilan", "Bataan", "Batanes", "Batangas",
            "Benguet", "Biliran", "Bohol", "Bukidnon", "Bulacan", "Cagayan",
            "Camarines Norte", "Camarines Sur", "Camiguin", "Capiz", "Catanduanes",
            "Cavite", "Cebu", "Cotabato", "Davao de Oro", "Davao del Norte",
            "Davao del Sur  ", "Davao Occidental", "Davao Oriental", "Dinagat Islands",
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

    public void initialize() {
        // Initialize combo boxes
        provinceComboBox.setItems(provinces);
        regionComboBox.setItems(regions);

    }

    public void setAddress(Address address) {
        this.addressToEdit = address;
        if (address != null) {
            AddAddressFormController.AdressField(address, streetField, barangayField, cityField, postalCodeField, provinceComboBox, regionComboBox, workRBTN, homeRBTN);
        }
    }

    public Address getUpdatedAddress() {
        return getAddress(streetField, cityField, provinceComboBox, regionComboBox, barangayField, postalCodeField, workRBTN);
    }

    @Nullable
    static Address getAddress(TextField streetField, TextField cityField, ComboBox<String> provinceComboBox, ComboBox<String> regionComboBox, TextField barangayField, TextField postalCodeField, RadioButton workRBTN) {
        if (streetField.getText().isEmpty() || cityField.getText().isEmpty() ||
                provinceComboBox.getValue() == null || regionComboBox.getValue() == null) {
            System.err.println("[ERROR] All required fields must be filled.");
            return null;
        }

        Address updatedAddress = new Address();
        updatedAddress.setStreet(streetField.getText());
        updatedAddress.setBarangay(barangayField.getText());
        updatedAddress.setCity(cityField.getText());
        updatedAddress.setPostalCode(postalCodeField.getText());
        updatedAddress.setProvince(provinceComboBox.getValue());
        updatedAddress.setRegion(regionComboBox.getValue());
        updatedAddress.setType(workRBTN.isSelected() ? "Work" : "Home");
        updatedAddress.setUserId(UserSession.getInstance().getUserId());

        return updatedAddress;
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
}
