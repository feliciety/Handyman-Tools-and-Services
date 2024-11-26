package project.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import project.demo.models.ShippingDetails;

import java.util.List;
import java.util.stream.Collectors;

public class DetailsController {

    private CartPageController mainController; // Reference to the main controller

    @FXML
    private AnchorPane detailsPane; // Reference to the root pane of Details.fxml

    @FXML
    private TextField contactField; // Contact input field

    @FXML
    private TextField addressField; // Address input field

    @FXML
    private TextField cityField; // City input field

    @FXML
    private TextField postalCodeField; // Postal Code input field

    @FXML
    private ComboBox<String> provinceComboBox; // Province dropdown

    @FXML
    private ComboBox<String> regionComboBox; // Region dropdown

    // List of provinces in the Philippines
    private final List<String> provinces = List.of(
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
            "Zamboanga del Norte", "Zamboanga del Sur", "Zamboanga Sibugay"
    );

    // List of regions in the Philippines
    private final List<String> regions = List.of(
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

    public void setMainController(CartPageController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        // Initialize ComboBox with provinces
        provinceComboBox.setItems(FXCollections.observableArrayList(provinces));
        provinceComboBox.setEditable(true); // Make the ComboBox editable

        // Add listener for filtering provinces
        provinceComboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            filterComboBox(provinceComboBox, provinces, newValue);
        });

        // Initialize ComboBox with regions
        regionComboBox.setItems(FXCollections.observableArrayList(regions));
        regionComboBox.setEditable(true); // Make the ComboBox editable

        // Add listener for filtering regions
        regionComboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            filterComboBox(regionComboBox, regions, newValue);
        });
    }

    private void filterComboBox(ComboBox<String> comboBox, List<String> items, String filter) {
        ObservableList<String> filteredItems = FXCollections.observableArrayList(
                items.stream()
                        .filter(item -> item.toLowerCase().contains(filter.toLowerCase()))
                        .collect(Collectors.toList())
        );

        comboBox.setItems(filteredItems);
        comboBox.getEditor().setText(filter);

        // Keep the dropdown open
        comboBox.show();
    }

    @FXML
    public void goToShipping(ActionEvent actionEvent) {
        if (mainController != null) {
            System.out.println("Navigating to Shipping view...");
            mainController.loadView("/project/demo/Shipping.fxml");
        } else {
            System.err.println("Main controller is not set!");
        }
    }

    @FXML
    public void goToCart(ActionEvent actionEvent) {
        if (mainController != null) {
            System.out.println("Navigating back to Cart view...");
            mainController.loadView("/project/demo/CartTable.fxml");
        } else {
            System.err.println("Main controller is not set!");
        }
    }

    private void saveDetails() {
        // Get the input details
        String contact = contactField.getText();
        String address = addressField.getText();
        String city = cityField.getText();
        String postalCode = postalCodeField.getText();
        String province = provinceComboBox.getValue();
        String region = regionComboBox.getValue();

        // Combine details into a formatted address
        String fullAddress = String.join(", ",
                address,
                city,
                postalCode,
                province,
                region
        );

        // Save to the shared model
        ShippingDetails.getInstance().setContact(contact);
        ShippingDetails.getInstance().setAddress(fullAddress);
    }
}
