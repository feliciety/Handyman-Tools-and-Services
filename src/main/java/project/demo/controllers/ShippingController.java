package project.demo.controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import project.demo.models.ShippingDetails;

import java.io.IOException;
import java.util.Optional;

public class ShippingController {

    private CartPageController mainController; // Reference to the main controller

    @FXML
    private Label contactLabel; // Label for contact information

    @FXML
    private Label addressLabel; // Label for shipping address

    @FXML
    private RadioButton standardShipping; // Standard shipping option

    @FXML
    private RadioButton expressShipping; // Express shipping option


    private final SimpleDoubleProperty shippingFee = new SimpleDoubleProperty(0.0); // Shipping fee

    @FXML
    public void initialize() {
        System.out.println("Initializing ShippingController...");

        // Initialize labels with data from ShippingDetails model
        ShippingDetails details = ShippingDetails.getInstance();
        if (contactLabel != null) contactLabel.setText(details.getContact());
        if (addressLabel != null) addressLabel.setText(details.getAddress());

        ToggleGroup shippingGroup = new ToggleGroup();
        standardShipping.setToggleGroup(shippingGroup);
        expressShipping.setToggleGroup(shippingGroup);

        shippingGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (standardShipping.isSelected()) {
                shippingFee.set(5.00);
            } else if (expressShipping.isSelected()) {
                shippingFee.set(15.00);
            }
            updateShippingPrice();
        });
    }

    public void setMainController(CartPageController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void editContact(ActionEvent actionEvent) {
        // Show input dialog to edit contact information
        TextInputDialog dialog = new TextInputDialog(contactLabel.getText());
        dialog.setTitle("Edit Contact");
        dialog.setHeaderText("Update your contact information");
        dialog.setContentText("Enter your email or phone number:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newContact -> {
            contactLabel.setText(newContact);
            ShippingDetails.getInstance().setContact(newContact);
        });
    }

    @FXML
    public void editAddress(ActionEvent actionEvent) {
        // Show input dialog to edit the shipping address
        TextInputDialog dialog = new TextInputDialog(addressLabel.getText());
        dialog.setTitle("Edit Address");
        dialog.setHeaderText("Update your shipping address");
        dialog.setContentText("Enter your new address:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newAddress -> {
            addressLabel.setText(newAddress);
            ShippingDetails.getInstance().setAddress(newAddress);
        });
    }

    @FXML
    public void goToPayment(ActionEvent actionEvent) {
        if (mainController != null) {
            System.out.println("Navigating to Payment view...");
            mainController.loadView("/project/demo/FXMLCartPage/Payment.fxml");
        } else {
            System.err.println("Main controller is not set!");
        }
    }

    @FXML
    public void goToDetails(ActionEvent actionEvent) {
        if (mainController != null) {
            System.out.println("Navigating back to Details view...");
            mainController.loadView("/project/demo/FXMLCartPage/Details.fxml");
        } else {
            System.err.println("Main controller is not set!");
        }
    }

    private void loadFXML(String fxmlPath, ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Set the new scene
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML file: " + fxmlPath);
        }
    }

    private void updateShippingPrice() {
        if (mainController != null) {
            mainController.setShippingFee(shippingFee.get());
        }
    }
}

