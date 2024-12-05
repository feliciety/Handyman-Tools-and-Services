package project.demo.controllers.Booking;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import project.demo.models.ServiceItem;
import project.demo.models.ServiceManager;

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

    private final ObservableList<ServiceItem> bookedItems = ServiceManager.getInstance().getServiceItems();
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

        bookedItems.addListener((ListChangeListener<ServiceItem>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(item -> {
                        // Replace complexityProperty() with the correct property
                        item.jobComplexityProperty().addListener((observable, oldValue, newValue) -> recalculateTotal());
                    });
                }
                recalculateTotal();
            }
        });

        // Recalculate total for any pre-existing items
        bookedItems.forEach(item -> {
            // Replace complexityProperty() with the correct property
            item.jobComplexityProperty().addListener((observable, oldValue, newValue) -> recalculateTotal());
        });
        recalculateTotal();
    }

    public void recalculateTotal() {
        // Sum the prices of all booked items
        double total = bookedItems.stream()
                .mapToDouble(ServiceItem::getPrice) // Get the price based on job complexity
                .sum();


        // Dynamically recalculate the coupon discount
        if (appliedCoupon != null && promoCodes.containsKey(appliedCoupon)) {
            double discount = promoCodes.get(appliedCoupon);

            // Check if the discount is a percentage or flat
            if (discount < 1.0) {
                couponDiscount.set(totalServiceFee.get() * discount); // Percentage discount
            } else {
                couponDiscount.set(Math.min(discount, totalServiceFee.get())); // Flat discount (capped at total)
            }
        } else {
            couponDiscount.set(0.0); // No discount
        }
    }


    @FXML
    public void applyPromoCode() {
        String promoCode = promoCodeField.getText().toUpperCase();
        if (promoCodes.containsKey(promoCode)) {
            appliedCoupon = promoCode;
            recalculateTotal();
            appliedCouponLabel.setText("Applied: " + promoCode);
            removeCouponButton.setVisible(true);
        }
    }

    @FXML
    public void removeCoupon() {
        appliedCoupon = null;
        couponDiscount.set(0.0);
        appliedCouponLabel.setText("No coupon applied");
        removeCouponButton.setVisible(false);
    }

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
            } else if (controller instanceof PaymentSuccessController) {
                ((PaymentSuccessController) controller).setMainController(this);
            }

            contentPane.getChildren().clear();
            contentPane.getChildren().add(newView);

            AnchorPane.setTopAnchor(newView, 0.0);
            AnchorPane.setBottomAnchor(newView, 0.0);
            AnchorPane.setLeftAnchor(newView, 0.0);
            AnchorPane.setRightAnchor(newView, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
