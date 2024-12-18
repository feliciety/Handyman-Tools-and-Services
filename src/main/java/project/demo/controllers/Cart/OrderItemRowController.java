package project.demo.controllers.Cart;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class OrderItemRowController {
    public HBox itemRow;
    public Label priceLabel;
    @FXML
    private Label totalPriceLabels;
    @FXML
    private Label nameLabel;

    @FXML
    private Label quantityLabel;

    /**
     * Sets the values for the order item row.
     *
     * @param productName the name of the product
     * @param quantity    the quantity of the product
     * @param totalPrice  the total price of the product
     */
    public void setOrderItemData(String productName, int quantity, double price, double totalPrice) {
        System.out.println("[DEBUG] setOrderItemData called.");
        System.out.println("[DEBUG] ProductName: " + productName + ", Quantity: " + quantity + ", TotalPrice: " + totalPrice);

        if (nameLabel == null || quantityLabel == null ||priceLabel == null|| totalPriceLabels == null) {
            System.err.println("[ERROR] Labels in OrderItemRowController are NULL. Check fx:id in FXML.");
            return;
        }

        nameLabel.setText(productName);
        quantityLabel.setText("Qty: " + quantity);
        priceLabel.setText(String.format("₱%.2f", price));
        totalPriceLabels.setText(String.format("₱%.2f", totalPrice));
        System.out.println("[DEBUG] Labels updated successfully.");
    }

}
