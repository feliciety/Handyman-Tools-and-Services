package project.demo.controllers.Profile;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ShippingStatusRowController {

    @FXML
    private Label orderNoLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Label OrderShippingStatus;

    public void setOrderData(int orderId, String orderDate, double totalPrice, String shippingStatus) {
        orderNoLabel.setText("Order #" + orderId);
        dateLabel.setText(orderDate);
        totalPriceLabel.setText(String.format("₱%.2f", totalPrice));

        // Update shipping status text and color dynamically
        OrderShippingStatus.setText(shippingStatus);

        switch (shippingStatus.toLowerCase()) {
            case "delivered":
                OrderShippingStatus.setStyle("-fx-text-fill: #1ecd97; -fx-font-size: 12px;");
                break;
            case "shipped":
                OrderShippingStatus.setStyle("-fx-text-fill: #ff9800; -fx-font-size: 12px;");
                break;
            case "processing":
                OrderShippingStatus.setStyle("-fx-text-fill: #ffc107; -fx-font-size: 12px;");
                break;
            default:
                OrderShippingStatus.setStyle("-fx-text-fill: #666666; -fx-font-size: 12px;");
                break;
        }
    }
}
