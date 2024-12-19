package project.demo.controllers.Profile;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import project.demo.models.UserSession;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceHistoryController {

    @FXML
    private GridPane ServiceStatusGrid;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/handyman_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static final Logger logger = Logger.getLogger(OrderHistoryController.class.getName());

    @FXML
    public void initialize() {
        populateOrderHistory();
    }

    private void populateOrderHistory() {
        String query = "SELECT booking_id, booking_date, service_fee, service_status FROM service_orders   WHERE booking_id = ?";
        int userId = UserSession.getInstance().getUserId(); // Replace with actual user session

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                int rowIndex = 0;
                while (resultSet.next()) {
                    int serviceNoLabel = resultSet.getInt("booking_id");
                    String dateLabel = resultSet.getString("booking_date");
                    double totalPriceLabel = resultSet.getDouble("service_fee");
                    String ServiceStatusLabel = resultSet.getString("service_status");

                    // Load the OrderRow FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLProfilePage/ServiceStatusRow.fxml"));
                    HBox orderRow = loader.load();

                    // Set data into the OrderRowController
                    ServiceStatusRowController controller = loader.getController();
                    controller.setOrderData(serviceNoLabel, dateLabel, totalPriceLabel, ServiceStatusLabel);

                    // Add row to the GridPane
                    ServiceStatusGrid.add(orderRow, 0, rowIndex++);
                }
            }
        } catch (SQLException | IOException e) {
            logger.log(Level.SEVERE, "Error loading order history", e);
        }
    }
}
