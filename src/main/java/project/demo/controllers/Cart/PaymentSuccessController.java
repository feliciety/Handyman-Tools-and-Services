package project.demo.controllers.Cart;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import project.demo.controllers.Main.MainStructureController;
import project.demo.models.CartManager;
import project.demo.models.UserSession;

import javafx.scene.image.WritableImage;
import javafx.scene.SnapshotParameters;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

public class PaymentSuccessController {

    @FXML
    public Label subtotalLabel;

    @FXML
    public Label couponDiscountLabel;

    @FXML
    public GridPane orderItemsGridPane;

    @FXML
    public Label shippingFeeLabel;

    @FXML
    public Label phoneNumberLabel;

    @FXML
    public Label nameLabel;

    @FXML
    public AnchorPane thankYouPane;

    @FXML
    public Button downloadReceiptBTN;

    @FXML
    public VBox ReceiptPane;

    @FXML
    private Button backToShopButton;

    @FXML
    private Label orderIdLabel;

    @FXML
    private Label orderDateLabel;

    @FXML
    private Label shippingMethodLabel;

    @FXML
    private Label shippingAddressLabel;

    @FXML
    private Label shippingNoteLabel;

    @FXML
    private Label paymentMethodLabel;

    @FXML
    private Label totalPriceLabel;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/handyman_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static final Logger logger = Logger.getLogger(PaymentSuccessController.class.getName());

    private CartPageController mainController;

    @FXML
    public void initialize() {
        System.out.println("[DEBUG] Initializing BookPaymentSuccessController...");
        validateFXMLBindings();
    }

    private void validateFXMLBindings() {
        if (backToShopButton == null) {
            logger.severe("[ERROR] backToShopButton is not properly initialized. Check fx:id in FXML.");
        }
        if (orderItemsGridPane == null) {
            logger.severe("[ERROR] orderItemsGridPane is not properly initialized. Check fx:id in FXML.");
        }
        System.out.println("[DEBUG] FXML Bindings validated.");
    }

    /**
     * Set order details and dynamically populate labels.
     */
    public void setOrderDetails(int orderId, double totalPrice, String shippingAddress,
                                String shippingMethod, String paymentMethod, String shippingNote, double shippingFee) {

        System.out.println("[DEBUG] setOrderDetails called for Order ID: " + orderId);

        nameLabel.setText(UserSession.getInstance().getUsername());
        phoneNumberLabel.setText(UserSession.getInstance().getContactNumber());
        orderIdLabel.setText("#" + orderId);
        orderDateLabel.setText(String.valueOf(java.time.LocalDate.now()));
        shippingAddressLabel.setText(shippingAddress);
        shippingMethodLabel.setText(shippingMethod);
        shippingNoteLabel.setText(shippingNote);
        paymentMethodLabel.setText(paymentMethod);
        shippingFeeLabel.setText(String.format("₱%.2f", shippingFee));
        totalPriceLabel.setText(String.format("₱%.2f", totalPrice));

        populateOrderItems(orderId);
        clearCartAfterDisplay();
        System.out.println("[DEBUG] Order details populated.");
    }

    /**
     * Clear the cart after displaying data.
     */
    private void clearCartAfterDisplay() {
        System.out.println("[INFO] Clearing cart after displaying all data...");
        CartManager.getInstance().clearCart();
    }

    /**
     * Set the Subtotal and Coupon Discount dynamically.
     */
    public void setSubtotalAndCoupon(String subtotal, String couponDiscount) {
        System.out.println("[DEBUG] setSubtotalAndCoupon called...");

        if (subtotalLabel != null) {
            subtotalLabel.setText(subtotal);
            System.out.println("[DEBUG] Subtotal updated: " + subtotal);
        } else {
            System.err.println("[ERROR] subtotalLabel is NULL.");
        }

        if (couponDiscountLabel != null) {
            couponDiscountLabel.setText(couponDiscount);
            System.out.println("[DEBUG] Coupon Discount updated: " + couponDiscount);
        } else {
            System.err.println("[ERROR] couponDiscountLabel is NULL.");
        }
    }

    /**
     * Populate the order items grid.
     */
    private void populateOrderItems(int orderId) {
        String query = "SELECT product_name, quantity, price FROM order_items WHERE order_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            int rowIndex = 0;

            while (rs.next()) {
                String productName = rs.getString("product_name");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                double totalPrice = quantity * price;

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLCartPage/OrderItemRow.fxml"));
                HBox row = loader.load();

                OrderItemRowController controller = loader.getController();
                controller.setOrderItemData(productName, quantity, price, totalPrice);

                orderItemsGridPane.add(row, 0, rowIndex++);
            }

            System.out.println("[DEBUG] Order items populated successfully.");
        } catch (SQLException | IOException e) {
            System.err.println("[ERROR] Failed to populate order items: " + e.getMessage());
        }
    }

    @FXML
    public void handleDownloadReceipt() {
        try {
            WritableImage snapshot = ReceiptPane.snapshot(new SnapshotParameters(), null);
            BufferedImage bufferedImage = new BufferedImage((int) snapshot.getWidth(), (int) snapshot.getHeight(), BufferedImage.TYPE_INT_ARGB);

            for (int y = 0; y < snapshot.getHeight(); y++) {
                for (int x = 0; x < snapshot.getWidth(); x++) {
                    bufferedImage.setRGB(x, y, snapshot.getPixelReader().getArgb(x, y));
                }
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Receipt");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
            fileChooser.setInitialFileName("HandymanReceipt" + orderIdLabel + ".png");

            File outputFile = fileChooser.showSaveDialog(new Stage());
            if (outputFile != null) {
                ImageIO.write(bufferedImage, "png", outputFile);
                System.out.println("[INFO] Receipt saved at: " + outputFile.getAbsolutePath());
            }

        } catch (Exception e) {
            System.err.println("[ERROR] Failed to save receipt: " + e.getMessage());
        }
    }

    @FXML
    public void handleBackToShop(ActionEvent event) {
        System.out.println("[INFO] Navigating back to the Shop Page...");

        try {
            // Load MainStructureController as the parent container
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/MainStructure.fxml"));
            AnchorPane mainStructure = loader.load();

            // Get the controller instance
            MainStructureController mainController = loader.getController();

            // Use the navigateTo method to load ShopPage
            mainController.navigateTo("/project/demo/FXMLShopPage/ShopPage.fxml");

            // Replace the current root with the MainStructure (and embedded Shop Page)
            Stage stage = (Stage) backToShopButton.getScene().getWindow();
            stage.getScene().setRoot(mainStructure);

            System.out.println("[INFO] Successfully navigated back to the Shop Page.");
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to navigate to the Shop Page: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void setMainController(CartPageController mainController) {
        this.mainController = mainController;
    }
}
