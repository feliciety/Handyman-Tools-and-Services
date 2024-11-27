package project.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class    PaymentController {

    private CartPageController mainController; // Reference to the main controller

    @FXML
    private Label totalItemsLabel;

    @FXML
    private Label totalAmountLabel;

    // Payment fields
    @FXML
    private VBox cardFields;

    @FXML
    private VBox paypalFields;

    @FXML
    private VBox bankFields;

    @FXML
    private VBox codFields;

    @FXML
    private TextField cardNameField;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField expiryField;

    @FXML
    private TextField cvvField;

    @FXML
    private TextField paypalEmailField;

    @FXML
    private TextField bankAccountNameField;

    @FXML
    private TextField bankAccountNumberField;

    @FXML
    private TextField bankNameField;

    @FXML
    private TextField bankBranchField;

    /**
     * Sets the main controller for interaction.
     */
    public void setMainController(CartPageController mainController) {
        this.mainController = mainController;
    }

    /**
     * Initialize the controller. This method is called after the FXML fields are injected.
     */
    @FXML
    public void initialize() {
        // Example: Populate total items and amount dynamically
        totalItemsLabel.setText("3");
        totalAmountLabel.setText("$120.00");
    }

    /**
     * Shows the fields for Credit/Debit Card payment method.
     */
    @FXML
    public void showCardFields() {
        setFieldsVisibility(true, false, false, false);
    }

    /**
     * Shows the fields for PayPal payment method.
     */
    @FXML
    public void showPayPalFields() {
        setFieldsVisibility(false, true, false, false);
    }

    /**
     * Shows the fields for Bank Transfer payment method.
     */
    @FXML
    public void showBankFields() {
        setFieldsVisibility(false, false, true, false);
    }

    /**
     * Shows the fields for Cash on Delivery payment method.
     */
    @FXML
    public void showCODFields() {
        setFieldsVisibility(false, false, false, true);
    }

    /**
     * Sets the visibility of payment method fields dynamically.
     */
    private void setFieldsVisibility(boolean showCard, boolean showPayPal, boolean showBank, boolean showCOD) {
        cardFields.setVisible(showCard);
        cardFields.setManaged(showCard);

        paypalFields.setVisible(showPayPal);
        paypalFields.setManaged(showPayPal);

        bankFields.setVisible(showBank);
        bankFields.setManaged(showBank);

        codFields.setVisible(showCOD);
        codFields.setManaged(showCOD);
    }

    /**
     * Navigates back to the Shipping view.
     */

    @FXML
    public void confirmPayment(ActionEvent actionEvent) {
        if (mainController != null) {
            System.out.println("Navigating to Payment Success view...");
            mainController.loadView("/project/demo/PaymentSuccess.fxml");
        } else {
            System.err.println("Main controller is not set!");
        }
    }

    @FXML
    public void backToShipping(ActionEvent actionEvent) {
        if (mainController != null) {
            System.out.println("Navigating back to Shipping view...");
            mainController.loadView("/project/demo/FXMLCartPage/Shipping.fxml");
        } else {
            System.err.println("Main controller is not set!");
        }
    }
}