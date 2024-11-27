package project.demo.controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    private AnchorPane contentPane; // Dynamic content pane

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
    private final SimpleDoubleProperty shippingFee = new SimpleDoubleProperty(10.0); // Example fixed fee
    private final SimpleDoubleProperty couponDiscount = new SimpleDoubleProperty(0.0);



    private final Map<String, Double> promoCodes = Map.of(
            "SAVE10", 10.0,         // Flat $10 discount
            "FREESHIP", 0.0,        // Free shipping
            "DISCOUNT20", 20.0,     // Flat $20 discount
            "SAVE5", 5.0,           // Flat $5 discount
            "BLACKFRIDAY", 15.0,    // Flat $15 discount for Black Friday
            "CYBERMONDAY", 20.0,    // Flat $20 discount for Cyber Monday
            "WELCOME", 10.0,        // Discount for new users
            "WINTER30", 0.3,       // $10 off for summer promotion
            "WINTER40", 0.4,
            "WINTER50", 0.5        //50% off subtotal for winter promotion
    );

    private String appliedCoupon = null;

    @FXML
    public void initialize() {
        // Bind subtotal label to subtotal property
        subtotalLabel.textProperty().bind(subtotal.asString("$%.2f"));
        couponDiscountLabel.textProperty().bind(couponDiscount.asString("-$%.2f"));

        // Set initial shipping label
        shippingLabel.setText("$" + shippingFee.get());
        updateTotal();

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
     * Recalculates the subtotal and total amounts based on the cart items.
     */
    public void recalculateSubtotal() {
        double total = cartItems.stream()
                .mapToDouble(item -> Double.parseDouble(item.getTotalPrice().replace("$", "")))
                .sum();
        subtotal.set(total);
        updateTotal();
    }

    /**
     * Updates the total amount based on subtotal, shipping fee, and applied discounts.
     */
    private void updateTotal() {
        double total = subtotal.get() + shippingFee.get();
        totalLabel.setText("$" + String.format("%.2f", total));
    }

    /**
     * Applies a promo code entered by the user.
     */
    @FXML
    public void applyPromoCode() {
        String promoCode = promoCodeField.getText().toUpperCase();
        if (promoCodes.containsKey(promoCode)) {
            double discount = promoCodes.get(promoCode);

            // Check if the discount is a percentage or flat
            if (discount < 1.0) {
                couponDiscount.set(subtotal.get() * discount); // Percentage discount
            } else {
                couponDiscount.set(discount); // Flat discount
            }

            appliedCoupon = promoCode;
            appliedCouponLabel.setText("Applied Coupon: " + promoCode);
            removeCouponButton.setVisible(true);

            updateTotal();
            System.out.println("Promo code applied: " + promoCode);
        } else {
            System.err.println("[ERROR] Invalid promo code: " + promoCode);
        }
    }


    /**
     * Dynamically loads a new view into the content pane.
     *
     * @param fxmlPath The FXML file path to load.
     */
    @FXML
    public void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane newView = loader.load();

            // Set reference to the main controller in subcontrollers
            Object controller = loader.getController();
            if (controller instanceof CartTableController) {
                ((CartTableController) controller).setMainController(this);
            } else if (controller instanceof DetailsController) {
                ((DetailsController) controller).setMainController(this);
            } else if (controller instanceof ShippingController) {
                ((ShippingController) controller).setMainController(this);
            } else if (controller instanceof PaymentController) {
                ((PaymentController) controller).setMainController(this);
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

    /**
     * Removes a cart item from the cartItems list and updates the subtotal.
     *
     * @param cartItem The CartItem to be removed.
     */
    public void removeCartItem(CartItem cartItem) {
        if (cartItem != null) {
            // Remove the item from the cart
            cartItems.remove(cartItem);

            // Recalculate the subtotal and update the UI
            recalculateSubtotal();

            System.out.println("[INFO] Removed item: " + cartItem.getProductName());
        } else {
            System.err.println("[ERROR] Cannot remove a null cart item.");
        }
    }

    public void removeCoupon() {
        couponDiscount.set(0.0);
        appliedCoupon = null;
        appliedCouponLabel.setText("No coupon applied");
        removeCouponButton.setVisible(false);
        updateTotal();
        System.out.println("Coupon removed.");
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee.set(shippingFee);
        shippingLabel.setText("$" + String.format("%.2f", shippingFee));
        updateTotal();
    }
}
