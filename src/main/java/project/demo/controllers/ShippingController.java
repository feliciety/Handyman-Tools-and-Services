package project.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import project.demo.models.ShippingDetails;

import java.util.Optional;

public class ShippingController {

    @FXML
    private Label contactLabel; // Label for contact information

    @FXML
    private Label addressLabel; // Label for shipping address

    private CartPageController mainController;

    public void setMainController(CartPageController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        System.out.println("Initializing ShippingController...");

        // Initialize labels with data from ShippingDetails model
        ShippingDetails details = ShippingDetails.getInstance();
        if (contactLabel != null) contactLabel.setText(details.getContact());
        if (addressLabel != null) addressLabel.setText(details.getAddress());
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
    public void goToCart(ActionEvent actionEvent) {
        if (mainController != null) {
            mainController.loadView("/project/demo/Details.fxml");
        }
    }

    @FXML
    public void goToPayment(ActionEvent actionEvent) {
        if (mainController != null) {
            mainController.setScene("/project/demo/Payment.fxml");
        }
    }
}
