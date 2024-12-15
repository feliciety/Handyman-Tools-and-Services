package project.demo.controllers.Cart;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OrderItemRowController {

    public Label totalPriceLabels;
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
    public void setOrderItemData(String productName, int quantity, double totalPrice) {
        nameLabel.setText(productName);
        quantityLabel.setText(String.valueOf(quantity));
        totalPriceLabels.setText(String.format("₱%.2f", totalPrice));
    }
}
