package project.demo.controllers.Booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import project.demo.controllers.Base.AbstractFormController;
import project.demo.dao.CreditCardDAO;
import project.demo.dao.CreditCardDAOImpl;
import project.demo.dao.GCashDAO;
import project.demo.dao.GCashDAOImpl;
import project.demo.dao.PayPalDAO;
import project.demo.dao.PayPalDAOImpl;
import project.demo.models.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class BookingPaymentController {


    @FXML
    private AnchorPane paymentDetailsBox;

    // DAO objects
    private final GCashDAO gcashDAO = new GCashDAOImpl();
    private final CreditCardDAO creditCardDAO = new CreditCardDAOImpl();
    private final PayPalDAO payPalDAO = new PayPalDAOImpl();

    private static final String DB_URL = "jdbc:mysql://localhost:3306/handyman_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private BookingPageController mainController; // Reference to the main controller
    private String selectedPaymentMethod = "COD"; // Default payment method

    public void setMainController(BookingPageController mainController) {
        this.mainController = mainController;
        System.out.println("[DEBUG] Main controller set in PaymentController.");
    }

    /**
     * Confirm the payment and navigate to the success page.
     */
    public void confirmPayment(ActionEvent actionEvent) {
        try {
            Address shippingAddress = AddressBookingDetailsController.getChosenAddress();
            String shippingNote = AddressBookingDetailsController.getInstance().getShippingNote();
            int userId = UserSession.getInstance().getUserId();

            System.out.println("[DEBUG] User ID: " + userId);
            System.out.println("[DEBUG] Shipping Address: " + shippingAddress.getFullAddress());

            int orderId = insertServiceOrders(userId, shippingAddress.getFullAddress(), shippingNote);

            if (orderId > 0) {
                insertBookedService(orderId);
                double subtotal = BookingPageController.getInstance().getSubtotal();
                navigateToSuccessPageWithPaymentDetails(orderId, shippingAddress.getFullAddress(), selectedPaymentMethod);
            } else {
                System.err.println("[ERROR] Order creation failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int insertServiceOrders(int userId, String serviceAddress, String additionalNotes) {
        String query = "INSERT INTO service_orders (user_id, service_address, payment_method, additional_notes, booking_date, service_fee, total_price, service_status) " +
                "VALUES (?, ?, ?, ?, NOW(), ?, ?, 'Pending')";
        int bookingId = -1;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            double serviceFee = BookingPageController.getInstance().getSubtotal();
            double totalPrice = serviceFee;

            // Set parameters
            stmt.setInt(1, userId);
            stmt.setString(2, serviceAddress); // Properly formatted address
            stmt.setString(3, selectedPaymentMethod);
            stmt.setString(4, additionalNotes);
            stmt.setDouble(5, serviceFee);
            stmt.setDouble(6, totalPrice);

            // Execute query
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    bookingId = generatedKeys.getInt(1);
                    System.out.println("[INFO] Service order created successfully. Booking ID: " + bookingId);
                }
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to insert service order: " + e.getMessage());
            e.printStackTrace();
        }
        return bookingId;
    }

    private void navigateToSuccessPageWithPaymentDetails(int bookingId, String additionalNotes, String paymentMethod) {
        try {
            System.out.println("[DEBUG] Navigating to BookingPaymentSuccess.fxml");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLBookingPage/BookingPaymentSuccess.fxml"));
            Parent successView = loader.load();

            BookPaymentSuccessController controller = loader.getController();
            if (controller == null) {
                System.err.println("[ERROR] BookPaymentSuccessController is NULL.");
                return;
            }

            double serviceFee = BookingPageController.getInstance().getSubtotal();
            controller.setOrderDetails(bookingId, serviceFee, AddressBookingDetailsController.getChosenAddress().getFullAddress(),
                    additionalNotes, paymentMethod);

            mainController.getContentPane().getChildren().setAll(successView);
            System.out.println("[DEBUG] Success page displayed.");
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to load BookingPaymentSuccess.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }



    private void insertBookedService(int serviceOrderId) {
        String query = "INSERT INTO booked_service (booking_id, service_name, job_complexity, service_fee) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (BookServiceItem item : BookServiceManager.getInstance().getBookedServices()) {
                stmt.setInt(1, serviceOrderId);
                stmt.setString(2, item.getServiceName());
                stmt.setString(3, item.jobComplexityProperty().get());
                stmt.setDouble(4, item.getServiceFee());
                stmt.addBatch();
            }

            int[] rowsInserted = stmt.executeBatch();
            System.out.println("[INFO] Booked services inserted. Rows affected: " + rowsInserted.length);
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to insert booked services: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void showGcashFields(ActionEvent actionEvent) {
        loadPaymentDetails("/project/demo/FXMLProfilePage/PaymentFXML/GCashEditPopup.fxml", "GCash");
    }

    @FXML
    public void showCardFields(ActionEvent actionEvent) {
        loadPaymentDetails("/project/demo/FXMLProfilePage/PaymentFXML/CreditCardEditPopup.fxml", "CreditCard");
    }

    @FXML
    public void showPayPalFields(ActionEvent actionEvent) {
        loadPaymentDetails("/project/demo/FXMLProfilePage/PaymentFXML/PayPalEditPopup.fxml", "PayPal");
    }

    @FXML
    public void showCODFields(ActionEvent actionEvent) {
        paymentDetailsBox.getChildren().clear();
        System.out.println("COD payment selected. No additional fields needed.");
    }

    private void loadPaymentDetails(String fxmlPath, String type) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane paymentPane = loader.load();

            AbstractFormController<?> controller = loader.getController();
            int userId = UserSession.getInstance().getUserId();

            // Populate fields based on payment type using AbstractFormController
            switch (type) {
                case "GCash":
                    GCash existingGCash = gcashDAO.getGCashByUserId(userId);
                    if (controller instanceof AbstractFormController<?>) {
                        AbstractFormController<GCash> gcashController = (AbstractFormController<GCash>) controller;
                        gcashController.setFields(existingGCash);
                        gcashController.hideButtons();
                    }
                    break;
                case "CreditCard":
                    CreditCard existingCard = creditCardDAO.getCreditCardByUserId(userId);
                    if (controller instanceof AbstractFormController<?>) {
                        AbstractFormController<CreditCard> cardController = (AbstractFormController<CreditCard>) controller;
                        cardController.setFields(existingCard);
                        cardController.hideButtons();
                    }
                    break;
                case "PayPal":
                    PayPal existingPayPal = payPalDAO.getPayPalByUserId(userId);
                    if (controller instanceof AbstractFormController<?>) {
                        AbstractFormController<PayPal> paypalController = (AbstractFormController<PayPal>) controller;
                        paypalController.setFields(existingPayPal);
                        paypalController.hideButtons();
                    }
                    break;
                default:
                    throw new IllegalArgumentException("[ERROR] Unsupported payment data type!");
            }

            paymentDetailsBox.getChildren().clear();
            paymentDetailsBox.getChildren().add(paymentPane);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to load payment details FXML: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void backToAddressBookingDetails(ActionEvent actionEvent) {
        mainController.goToAddressBookingDetails();
        System.out.println("[DEBUG] Back to details page.");
    }
}
