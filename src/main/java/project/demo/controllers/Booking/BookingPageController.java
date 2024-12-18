package project.demo.controllers.Booking;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import project.demo.controllers.Booking.AddressBookingDetailsController;
import project.demo.controllers.Cart.LoadingPageCartController;
import project.demo.models.BookServiceItem;
import project.demo.models.BookServiceManager;

import java.io.IOException;
import java.util.Map;

public class BookingPageController {

    @FXML
    private AnchorPane contentPane; // Dynamic content pane

    @FXML
    private Label totalServiceFeeLabel;

    @FXML
    private Label couponDiscountLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private Label appliedCouponLabel;

    @FXML
    private Button removeCouponButton;

    @FXML
    private TextField promoCodeField;

    private static BookingPageController instance;

    public static BookingPageController getInstance() {
        return instance;
    }

    public AnchorPane getContentPane() {
        return contentPane;
    }


    private final ObservableList<BookServiceItem> bookedItems = BookServiceManager.getInstance().getBookedServices();
    private final SimpleDoubleProperty totalServiceFee = new SimpleDoubleProperty(0.0);
    private final SimpleDoubleProperty couponDiscount = new SimpleDoubleProperty(0.0);

    private final Map<String, Double> promoCodes = Map.of(
            "SERVE10", 10.0,
            "DISCOUNT20", 20.0,
            "SAVE5", 5.0,
            "WELCOME", 10.0,
            "BOOK30", 0.3,
            "BOOK40", 0.4,
            "BOOK50", 0.5
    );

    private String appliedCoupon = null;

    @FXML
    public void initialize() {
        loadView("/project/demo/FXMLBookingPage/BookingCartTable.fxml");

        // Listen for changes in booked items
        bookedItems.addListener((ListChangeListener<BookServiceItem>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(item -> {
                                item.jobComplexityProperty().addListener((observable, oldValue, newValue) -> recalculateTotal());
                            }
                    );
                }
            }
            recalculateTotal();
        });

        // Add listeners to existing items
        bookedItems.forEach(item ->
                item.jobComplexityProperty().addListener((observable, oldValue, newValue) -> recalculateTotal())
        );

        recalculateTotal();
    }

    /**
     * Recalculates the total service fee and applies any active coupon discounts.
     */
    public void recalculateTotal() {
        double total = bookedItems.stream()
                .mapToDouble(BookServiceItem::getServiceFee)
                .sum();
        totalServiceFee.set(total);

        if (appliedCoupon != null && promoCodes.containsKey(appliedCoupon)) {
            double discount = promoCodes.get(appliedCoupon);
            if (discount < 1.0) {
                couponDiscount.set(totalServiceFee.get() * discount); // Percentage discount
            } else {
                couponDiscount.set(Math.min(discount, totalServiceFee.get())); // Flat discount (capped at total)
            }
        } else {
            couponDiscount.set(0.0); // No discount
        }

        updateLabels();
    }

    /**
     * Updates the total labels in the UI.
     */
    private void updateLabels() {
        totalServiceFeeLabel.setText(String.format("₱%.2f", totalServiceFee.get()));
        couponDiscountLabel.setText(String.format("-₱%.2f", couponDiscount.get()));
        totalLabel.setText(String.format("₱%.2f", totalServiceFee.get() - couponDiscount.get()));
    }

    /**
     * Applies a promo code and recalculates totals.
     */
    @FXML
    public void applyPromoCode() {
        String promoCode = promoCodeField.getText().toUpperCase().trim();
        if (promoCodes.containsKey(promoCode)) {
            appliedCoupon = promoCode;
            recalculateTotal();
            appliedCouponLabel.setText("Applied: " + promoCode);
            removeCouponButton.setVisible(true);
        } else {
            appliedCouponLabel.setText("Invalid Coupon Code");
        }
    }

    /**
     * Removes the currently applied promo code.
     */
    @FXML
    public void removeCoupon() {
        appliedCoupon = null;
        couponDiscount.set(0.0);
        appliedCouponLabel.setText("No coupon applied");
        removeCouponButton.setVisible(false);
        recalculateTotal();
    }

    /**
     * Loads a new FXML view into the content pane.
     *
     * @param fxmlPath The path to the FXML file to load.
     */
    @FXML
    public void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane newView = loader.load();
            Object controller = loader.getController();

            if (controller instanceof BookingCartTableController) {
                ((BookingCartTableController) controller).setMainController(this);
            } else if (controller instanceof AddressBookingDetailsController) {
                ((AddressBookingDetailsController) controller).setMainController(this);
            } else if (controller instanceof BookingPaymentController) {
                ((BookingPaymentController) controller).setMainController(this);
            } else if (controller instanceof BookPaymentSuccessController) {
                ((BookPaymentSuccessController) controller).setMainController(this);
            }

            contentPane.getChildren().clear();
            contentPane.getChildren().add(newView);

            AnchorPane.setTopAnchor(newView, 0.0);
            AnchorPane.setBottomAnchor(newView, 0.0);
            AnchorPane.setLeftAnchor(newView, 0.0);
            AnchorPane.setRightAnchor(newView, 0.0);

        } catch (IOException e) {
            System.err.println("[ERROR] Failed to load FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }

    private void loadViewWithLoading(String targetFxmlPath, String gifKey) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLBookingPage/LoadingPageBooking.fxml"));
            Parent loadingView = loader.load();

            // Get the controller for the loading page
            LoadingPageBookingController loadingController = loader.getController();

            // Initialize the loading screen and provide it with the target FXML and GIF key
            loadingController.initializeLoading(contentPane, targetFxmlPath, gifKey, this);

            // Display the loading screen in the contentPane
            contentPane.getChildren().setAll(loadingView);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to load LoadingPageCart.fxml.");
        }
    }



    public void goToBookingCartTable() {

        loadViewWithLoading("/project/demo/FXMLBookingPage/BookingCartTable.fxml", "loadingBookingCartTable");
    }

    public void goToAddressBookingDetails() {
        loadViewWithLoading("/project/demo/FXMLBookingPage/AddressBookingDetails.fxml", "loadingAddressBookingDetails");
    }

    public void goToPayment() {
        loadViewWithLoading("/project/demo/FXMLBookingPage/BookingPayment.fxml", "loadingBookingPayment");
    }

    public void confirmBookingPayment() {
        loadViewWithLoading("/project/demo/FXMLBookingPage/BookingPaymentSuccess.fxml", "loadingBookPaymentSuccess");
    }

    public double getSubtotal() {
        return totalServiceFee.get();
    }

    public double getCouponDiscount() {
        return couponDiscount.get();
    }


}
