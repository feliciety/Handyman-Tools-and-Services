package project.demo.controllers.Cart;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import project.demo.models.CartItem;
import project.demo.models.CartManager;

import java.io.IOException;
import java.util.Map;

public class CartPageController {

    @FXML
    AnchorPane contentPane; // Dynamic content pane

    @FXML
    private Label subtotalLabel;

    @FXML
    private Label shippingLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private Label couponDiscountLabel;

    @FXML
    private Label appliedCouponLabel;

    @FXML
    private Button removeCouponButton;

    @FXML
    private TextField promoCodeField;

    private final ObservableList<CartItem> cartItems = CartManager.getInstance().getCartItems();
    private final SimpleDoubleProperty subtotal = new SimpleDoubleProperty(0.0);
    private final SimpleDoubleProperty shippingFee = new SimpleDoubleProperty(0.0); // Dynamic shipping fee
    private final SimpleDoubleProperty couponDiscount = new SimpleDoubleProperty(0.0);

    private final Map<String, Double> promoCodes = Map.of(
            "SAVE10", 10.0,         // Flat $10 discount
            "FREESHIP", 0.0,        // Free shipping
            "DISCOUNT20", 20.0,     // Flat $20 discount
            "SAVE5", 5.0,           // Flat $5 discount
            "BLACKFRIDAY", 15.0,    // Flat $15 discount for Black Friday
            "CYBERMONDAY", 20.0,    // Flat $20 discount for Cyber Monday
            "WELCOME", 10.0,        // Discount for new users
            "WINTER30", 0.3,        // 30% off
            "WINTER40", 0.4,        // 40% off
            "WINTER50", 0.5         // 50% off
    );

    private String appliedCoupon = null;

    @FXML
    public void initialize() {
        // Bind labels to their respective properties
        subtotalLabel.textProperty().bind(subtotal.asString("₱%.2f"));
        shippingLabel.textProperty().bind(shippingFee.asString("₱%.2f"));
        couponDiscountLabel.textProperty().bind(couponDiscount.asString("-₱%.2f"));
        totalLabel.textProperty().bind(
                subtotal.subtract(couponDiscount).add(shippingFee).asString("₱%.2f")
        );

        // Add listener to cart items for real-time updates
        cartItems.addListener((ListChangeListener<CartItem>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(item -> {
                        item.quantityProperty().addListener((observable, oldValue, newValue) -> recalculateSubtotal());
                    });
                }
                recalculateSubtotal();
            }
        });

        // Load initial view (e.g., CartTable)
        loadView("/project/demo/FXMLCartPage/CartTable.fxml");

        // Recalculate subtotal for any pre-existing items
        cartItems.forEach(item -> {
            item.quantityProperty().addListener((observable, oldValue, newValue) -> recalculateSubtotal());
        });
        recalculateSubtotal();
    }

    /**
     * Updates the shipping fee dynamically and recalculates the total.
     *
     * @param fee The new shipping fee.
     */
    public void updateShippingFee(double fee) {
        System.out.println("[DEBUG] Shipping fee updated to: " + fee);
        shippingFee.set(fee);
        recalculateTotal();
    }

    /**
     * Recalculates the subtotal and updates the coupon discount dynamically.
     */
    public void recalculateSubtotal() {
        double total = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        subtotal.set(total);

        // Dynamically recalculate the coupon discount
        if (appliedCoupon != null && promoCodes.containsKey(appliedCoupon)) {
            double discount = promoCodes.get(appliedCoupon);

            // Check if the discount is a percentage or flat
            if (discount < 1.0) {
                couponDiscount.set(subtotal.get() * discount); // Percentage discount
            } else {
                couponDiscount.set(Math.min(discount, subtotal.get())); // Flat discount (capped at subtotal)
            }
        } else {
            couponDiscount.set(0.0); // No discount
        }
    }

    /**
     * Recalculates the total dynamically.
     */
    private void recalculateTotal() {
        double totalAmount = subtotal.get() - couponDiscount.get() + shippingFee.get();
        totalLabel.setText(String.format("₱%.2f", totalAmount));
        System.out.println("[DEBUG] Total recalculated: " + totalAmount);
    }

    /**
     * Applies a promo code entered by the user.
     */
    @FXML
    public void applyPromoCode() {
        String promoCode = promoCodeField.getText().toUpperCase();
        if (promoCodes.containsKey(promoCode)) {
            appliedCoupon = promoCode;

            // Dynamically update the discount based on the current subtotal
            recalculateSubtotal();

            appliedCouponLabel.setText("Applied: " + promoCode);
            removeCouponButton.setVisible(true);

            System.out.println("Promo code applied: " + promoCode);
        } else {
            System.err.println("[ERROR] Invalid promo code: " + promoCode);
        }
    }

    /**
     * Removes the applied promo code.
     */
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
            if (controller instanceof CartTableController) {
                ((CartTableController) controller).setMainController(this);
            } else if (controller instanceof DetailsController) {
                ((DetailsController) controller).setMainController(this);
            } else if (controller instanceof ShippingController) {
                ShippingController shippingController = (ShippingController) controller;
                shippingController.setMainController(this);
                shippingController.setShippingFeeListener(shippingFee);
            } else if (controller instanceof PaymentController) {
                ((PaymentController) controller).setMainController(this);
            } else if (controller instanceof PaymentSuccessController) {
                ((PaymentSuccessController) controller).setMainController(this);
            }

            contentPane.getChildren().clear();
            contentPane.getChildren().add(newView);
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to load FXML file: " + fxmlPath);
            e.printStackTrace();
        }
    }

    public void removeCartItem(CartItem cartItem) {
        if (cartItem != null) {
            cartItems.remove(cartItem);
            recalculateSubtotal();
            System.out.println("[INFO] Removed item: " + cartItem.getProductName());
        } else {
            System.err.println("[ERROR] Cannot remove a null cart item.");
        }
    }
}
