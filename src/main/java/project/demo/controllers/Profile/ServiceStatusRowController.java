package project.demo.controllers.Profile;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ServiceStatusRowController {

    @FXML public Label serviceNoLabel;
    @FXML public Label ServiceStatusLabel;
    @FXML
    private Label dateLabel;

    @FXML
    private Label totalPriceLabel;


    public void setOrderData(int orderId, String orderDate, double totalPrice, String serviceStatus) {
        serviceNoLabel.setText("Order #" + orderId);
        dateLabel.setText(orderDate);
        totalPriceLabel.setText(String.format("₱%.2f", totalPrice));

        // Update shipping status text and color dynamically
        ServiceStatusLabel.setText(serviceStatus);

        switch (serviceStatus.toLowerCase()) {
            case "complete":
                ServiceStatusLabel.setStyle("-fx-text-fill: #1ecd97; -fx-font-size: 12px;");
                break;
            case "on progress":
                ServiceStatusLabel.setStyle("-fx-text-fill: #ff9800; -fx-font-size: 12px;");
                break;
            case "pending":
                ServiceStatusLabel.setStyle("-fx-text-fill: #ffc107; -fx-font-size: 12px;");
                break;
            default:
                ServiceStatusLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12px;");
                break;
        }
    }
}
