package project.demo.controllers.Cart;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import project.demo.DataBase.DatabaseConfig;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShippingController {

    private CartPageController mainController; // Reference to the main controller

    @FXML
    private RadioButton standardShipping; // Standard shipping option

    @FXML
    private RadioButton expressShipping; // Express shipping option

    @FXML
    private RadioButton priorityShipping; // Priority shipping option

    private int orderId; // Holds the order ID for reference

    private ToggleGroup shippingGroup; // ToggleGroup for radio buttons
    private final SimpleDoubleProperty shippingFee = new SimpleDoubleProperty(0.0); // Holds the shipping fee
    private String selectedShippingMethod;
    private String shippingNote; // Holds the shipping note

    private static ShippingController instance;

    public ShippingController() {
        instance = this; // Set the static instance
    }

    public static ShippingController getInstance() {
        return instance;
    }

    @FXML
    public void initialize() {
        System.out.println("[DEBUG] ShippingController initialized.");

        // Initialize ToggleGroup
        shippingGroup = new ToggleGroup();
        standardShipping.setToggleGroup(shippingGroup);
        expressShipping.setToggleGroup(shippingGroup);
        priorityShipping.setToggleGroup(shippingGroup);

        // Set shipping fee dynamically
        shippingGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("[DEBUG] Shipping option changed.");
            updateSelectedShipping();
            notifyMainController();
        });

        // Default selection
        standardShipping.setSelected(true);
        updateSelectedShipping();
    }

    /**
     * Updates the selected shipping method and fee.
     */
    private void updateSelectedShipping() {
        if (standardShipping.isSelected()) {
            updateShippingDetails("Standard Shipping", 250.00);
        } else if (expressShipping.isSelected()) {
            updateShippingDetails("Express Shipping", 750.00);
        } else if (priorityShipping.isSelected()) {
            updateShippingDetails("Priority Shipping", 1250.00);
        } else {
            System.out.println("[DEBUG] No shipping option selected.");
        }
    }

    /**
     * Updates the shipping details locally.
     */
    private void updateShippingDetails(String method, double fee) {
        selectedShippingMethod = method;
        shippingFee.set(fee);
        System.out.println("[DEBUG] Selected Shipping Method: " + method + " | Fee: $" + fee);
    }

    public double getShippingFee() {
        return shippingFee.get();
    }

    public String getShippingNote() {
        return String.valueOf(DetailsController.getShippingNote()); // Retrieve shipping note dynamically
    }


    /**
     * Allows external controllers to listen for shipping fee updates.
     */
    public void setShippingFeeListener(SimpleDoubleProperty cartShippingFee) {
        shippingFee.addListener((observable, oldValue, newValue) -> {
            System.out.println("[DEBUG] Shipping Fee Updated: " + newValue);
            cartShippingFee.set(newValue.doubleValue());
        });
    }

    /**
     * Notifies the CartPageController about the updated shipping fee.
     */
    private void notifyMainController() {
        if (mainController != null) {
            mainController.updateShippingFee(shippingFee.get());
            System.out.println("[DEBUG] Notified CartPageController of new shipping fee.");
        }
    }

    public String getSelectedShippingMethod() {
        System.out.println("[DEBUG] getSelectedShippingMethod() called: " + selectedShippingMethod);
        return selectedShippingMethod != null ? selectedShippingMethod : "No Shipping Method Selected";
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
        System.out.println("[DEBUG] Order ID set to: " + orderId);
    }

    /**
     * Confirms shipping method and inserts details into the database.
     */
    @FXML
    public void confirmShipping(ActionEvent actionEvent) {
        System.out.println("[DEBUG] confirmShipping() called.");
        if (orderId == 0) {
            System.err.println("[ERROR] Order ID is missing. Cannot save shipping details.");
            return;
        }

        System.out.println("[INFO] Shipping Details Confirmed:");
        System.out.println("Method: " + selectedShippingMethod);
        System.out.println(String.format("Fee: $%.2f", shippingFee.get()));
        System.out.println("Note: " + (shippingNote != null ? shippingNote : "No Note"));

        insertShippingDetailsIntoDatabase();
    }

    private void insertShippingDetailsIntoDatabase() {
        System.out.println("[DEBUG] insertShippingDetailsIntoDatabase() called.");

        String insertSQL = "INSERT INTO shipping_note (order_id, method, fee, note) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

            System.out.println("[DEBUG] Database connection established.");
            stmt.setInt(1, orderId);
            stmt.setString(2, selectedShippingMethod);
            stmt.setDouble(3, shippingFee.get());
            stmt.setString(4, shippingNote != null ? shippingNote : ""); // Save shipping note

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("[INFO] Shipping details saved successfully for Order ID: " + orderId);
            } else {
                System.err.println("[ERROR] Failed to save shipping details.");
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setMainController(CartPageController mainController) {
        this.mainController = mainController;
        System.out.println("[DEBUG] Main controller set in ShippingController.");
    }

    @FXML
    public AnchorPane contentPane;

    @FXML
    public void goToPayment(ActionEvent actionEvent) {
        mainController.goToPayment();
    }

    @FXML
    public void goToDetails(ActionEvent actionEvent) {
        mainController.goToDetails();
    }
}