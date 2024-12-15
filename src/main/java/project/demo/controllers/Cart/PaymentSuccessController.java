package project.demo.controllers.Cart;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;

public class PaymentSuccessController {

    @FXML private Label orderIdLabel;
    @FXML private Label totalPriceLabel;
    @FXML private Label shippingAddressLabel;
    @FXML private Label shippingMethodLabel;
    @FXML private Label paymentMethodLabel;
    @FXML private Label orderDateLabel;
    @FXML private Label shippingNoteLabel;
    @FXML private Button backToHomeButton;
    @FXML private AnchorPane successPage;

    private int orderId;
    private double totalPrice;
    private String shippingAddress, shippingMethod, paymentMethod, shippingNote;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/handyman_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private CartPageController mainController;

    @FXML
    public void initialize() {
        backToHomeButton.setOnAction(event -> handleBackToHome());
        System.out.println("[DEBUG] PaymentSuccessController initialized.");
    }

    public void setOrderDetails(int orderId, double totalPrice, String shippingAddress,
                                String shippingMethod, String paymentMethod, String shippingNote) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.shippingAddress = shippingAddress;
        this.shippingMethod = shippingMethod;
        this.paymentMethod = paymentMethod;
        this.shippingNote = shippingNote;

        updateLabels();
    }

    private void updateLabels() {
        orderIdLabel.setText(String.valueOf(orderId));
        totalPriceLabel.setText(String.format("%.2f", totalPrice));
        shippingAddressLabel.setText(shippingAddress);
        shippingMethodLabel.setText(shippingMethod);
        paymentMethodLabel.setText(paymentMethod);
        orderDateLabel.setText(String.valueOf(LocalDate.now()));
        shippingNoteLabel.setText(shippingNote);
    }

    private void handleBackToHome() {
        Stage stage = (Stage) backToHomeButton.getScene().getWindow();
        stage.close();
        System.out.println("[INFO] Navigated back to home.");
    }

    public void setMainController(CartPageController mainController) {
        this.mainController = mainController;
        System.out.println("[DEBUG] Main controller set in ShippingController.");
    }

}
