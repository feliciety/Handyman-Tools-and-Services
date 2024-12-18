package project.demo.controllers.Booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import project.demo.controllers.Base.AbstractFormController;
import project.demo.dao.*;
import project.demo.models.CreditCard;
import project.demo.models.GCash;
import project.demo.models.PayPal;
import project.demo.models.UserSession;

import java.io.IOException;
import java.sql.*;

public class BookingPaymentController {

    @FXML
    private AnchorPane paymentDetailsBox;


    private final GCashDAO gcashDAO = new GCashDAOImpl();
    private final CreditCardDAO creditCardDAO = new CreditCardDAOImpl();
    private final PayPalDAO payPalDAO = new PayPalDAOImpl();

    private static final String DB_URL = "jdbc:mysql://localhost:3306/handyman_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private BookingPageController mainController;

    private String selectedPaymentMethod = "COD"; // Default payment method

    public void setMainController(BookingPageController mainController) {
        this.mainController = mainController;
    }

    /**
     * Confirm the payment and create a new service order.
     */
    @FXML
    public void confirmPayment(ActionEvent actionEvent) {
        try {
            int userId = UserSession.getInstance().getUserId();
            String serviceAddress = AddressBookingDetailsController.getChosenAddress().getFullAddress();
            String paymentMethod = selectedPaymentMethod;

            int bookingId = insertBookedService(userId, serviceAddress, paymentMethod);

            if (bookingId > 0) {
                insertBookedServiceItems(bookingId);
                navigateToSuccessPage(bookingId);
            } else {
                System.err.println("[ERROR] Service booking creation failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert a new record into service_orders.
     */
    private int insertBookedService(int userId, String serviceAddress, String paymentMethod) {
        String query = "INSERT INTO service_orders (user_id, service_address, payment_method, total_price, booking_date, service_status) VALUES (?, ?, ?, ?, NOW(), 'Processing')";
        int bookingId = -1;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            double totalPrice = BookingPageController.getInstance().getTotalPrice();

            stmt.setInt(1, userId);
            stmt.setString(2, serviceAddress);
            stmt.setString(3, paymentMethod);
            stmt.setDouble(4, totalPrice);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    bookingId = generatedKeys.getInt(1);
                    System.out.println("[INFO] Service booking created successfully. Booking ID: " + bookingId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingId;
    }

    /**
     * Insert services into the booked_services table.
     */
    private void insertBookedServiceItems(int bookingId) {
        String query = "INSERT INTO booked_services (booking_id, service_id, service_name, price, quantity, subtotal) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (BookedServiceItem item : BookingCartTableController.getInstance().getBookedServices()) {
                stmt.setInt(1, bookingId);
                stmt.setInt(2, item.getService().getServiceId());
                stmt.setString(3, item.getService().getServiceName());
                stmt.setDouble(4, item.getService().getServicePrice());
                stmt.setInt(5, item.getQuantity());
                stmt.setDouble(6, item.getSubtotal());
                stmt.addBatch();
            }

            int[] rowsInserted = stmt.executeBatch();
            System.out.println("[INFO] Booked services inserted. Rows affected: " + rowsInserted.length);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigate to the success page after confirming payment.
     */
    private void navigateToSuccessPage(int bookingId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLBookingPage/BookingPaymentSuccess.fxml"));
            Parent successView = loader.load();

            BookingPaymentSuccessController controller = loader.getController();
            double totalPrice = BookingCartController.getInstance().getTotalPrice();
            controller.setBookingDetails(bookingId, totalPrice);

            mainController.getContentPane().getChildren().setAll(successView);
            System.out.println("[INFO] Booking success page displayed.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to load BookingSuccess.fxml");
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

    public void backToAddressBookingDetails(ActionEvent actionEvent) {
        mainController.goToAddressBookingDetails();
    }

}