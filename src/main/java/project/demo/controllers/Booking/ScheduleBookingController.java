package project.demo.controllers.Booking;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import project.demo.controllers.Cart.CartPageController;

public class ScheduleBookingController {

    private BookingPageController mainController; // Reference to the main controller

    @FXML
    private RadioButton standardShipping; // Standard shipping option

    @FXML
    private RadioButton expressShipping; // Express shipping option

    @FXML
    private RadioButton priorityShipping;


    private final SimpleDoubleProperty shippingFee = new SimpleDoubleProperty(0.0); // Shipping fee

    @FXML
    public void initialize() {
        System.out.println("Initializing ShippingController...");

        // Setup ToggleGroup for shipping methods
        ToggleGroup shippingGroup = new ToggleGroup();
        standardShipping.setToggleGroup(shippingGroup);
        expressShipping.setToggleGroup(shippingGroup);
        priorityShipping.setToggleGroup(shippingGroup);

        // Add listener to update the shipping fee dynamically
        shippingGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (standardShipping.isSelected()) {
                shippingFee.set(5.00);
            } else if (expressShipping.isSelected()) {
                shippingFee.set(15.00);
            } else if (priorityShipping.isSelected()) {
                shippingFee.set(25.00);
            }
            updateShippingPrice();
        });
    }

    public void setMainController(BookingPageController mainController) {
        this.mainController = mainController;
    }


    @FXML
    public void goToPayment(ActionEvent actionEvent) {
        if (mainController != null) {
            mainController.loadView("/project/demo/FXMLCartPage/Payment.fxml");
        } else {
            System.err.println("Main controller is not set!");
        }
    }

    @FXML
    public void goToDetails(ActionEvent actionEvent) {
        if (mainController != null) {
            mainController.loadView("/project/demo/FXMLCartPage/Details.fxml");
        } else {
            System.err.println("Main controller is not set!");
        }
    }

    private void updateShippingPrice() {
        if (mainController != null) {
            mainController.setShippingFee(shippingFee.get());
        }
    }
}

