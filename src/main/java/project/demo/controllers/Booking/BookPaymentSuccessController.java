package project.demo.controllers.Booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import project.demo.controllers.Main.MainStructureController;
import project.demo.models.BookServiceManager;
import project.demo.models.UserSession;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;


public class BookPaymentSuccessController {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/handyman_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    @FXML
   public Button downloadReceiptBTN;
    @FXML
    public Label totalPriceLabel;
    @FXML
    public Label couponDiscountLabel;
    @FXML public Label shippingAddressLabel;
    @FXML  public Label bookingDateLabel;
    @FXML  public Label bookingIdLabel;
    @FXML public Label phoneNumberLabel;
    @FXML  public Label nameLabel;
    @FXML  public Label paymentMethodLabel;
    @FXML  public VBox ReceiptPane;
    @FXML  public Label shippingNoteLabel;
    public Label subtotalLabel;
    public GridPane orderItemsGridPane;

    public Button backToServicesBTN;
    @FXML  private BookingPageController mainController;


    public void setOrderDetails(int bookingId, double totalPrice, String shippingAddress, String paymentMethod, String method) {

        System.out.println("[DEBUG] setOrderDetails called for Order ID: " + bookingId);

        nameLabel.setText(UserSession.getInstance().getUsername());
        phoneNumberLabel.setText(UserSession.getInstance().getContactNumber());
        bookingIdLabel.setText("#" + bookingId);
        bookingDateLabel.setText(String.valueOf(java.time.LocalDate.now()));
        shippingAddressLabel.setText(shippingAddress);
        paymentMethodLabel.setText(paymentMethod);
        totalPriceLabel.setText(String.format("₱%.2f", totalPrice));

        populateServiceRows(bookingId);
        clearCartAfterDisplay();
        System.out.println("[DEBUG] Order details populated.");
    }

    private void clearCartAfterDisplay() {
        System.out.println("[INFO] Clearing cart after displaying all data...");
        BookServiceManager.getInstance().clearService();
    }
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

    private void populateServiceRows(int bookingId) {
        String query = "SELECT service_name, service_fee, job_complexity FROM booked_service WHERE booking_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            int rowIndex = 0;

            while (rs.next()) {
                String serviceName = rs.getString("service_name");
                double serviceFee = rs.getDouble("service_fee");
                String jobComplexity = rs.getString("job_complexity");

                // Load the BookedServiceRow FXML dynamically
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLBookingPage/BookedServiceRow.fxml"));
                HBox row = loader.load();

                // Set data for the row controller
                BookedServiceRowController controller = loader.getController();
                controller.setServiceData(serviceName, serviceFee, jobComplexity);

                // Add row to the grid
                orderItemsGridPane.add(row, 0, rowIndex++);
            }

            System.out.println("[DEBUG] Service rows populated successfully.");
        } catch (SQLException | IOException e) {
            System.err.println("[ERROR] Failed to populate booked services: " + e.getMessage());
            e.printStackTrace();
        }
    }



    public void setMainController(BookingPageController mainController) {
        this.mainController = mainController;
    }

    public void handleDownloadReceipt(ActionEvent actionEvent) {
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
            fileChooser.setInitialFileName("HandymanReceipt" + bookingIdLabel + ".png");

            File outputFile = fileChooser.showSaveDialog(new Stage());
            if (outputFile != null) {
                ImageIO.write(bufferedImage, "png", outputFile);
                System.out.println("[INFO] Receipt saved at: " + outputFile.getAbsolutePath());
            }

        } catch (Exception e) {
            System.err.println("[ERROR] Failed to save receipt: " + e.getMessage());
        }
    }
    public void handleBackToServices(ActionEvent actionEvent) {
        try {
            // Load MainStructureController as the parent container
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/MainStructure.fxml"));
            AnchorPane mainStructure = loader.load();

            // Get the controller instance
            MainStructureController mainController = loader.getController();

            // Use the navigateTo method to load ShopPage
            mainController.navigateTo("/project/demo/FXMLServicePage/ServicePage.fxml");

            // Replace the current root with the MainStructure (and embedded Shop Page)
            Stage stage = (Stage) backToServicesBTN.getScene().getWindow();
            stage.getScene().setRoot(mainStructure);

            System.out.println("[INFO] Successfully navigated back to the Shop Page.");
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to navigate to the Shop Page: " + e.getMessage());
            e.printStackTrace();
        }
    }

}

