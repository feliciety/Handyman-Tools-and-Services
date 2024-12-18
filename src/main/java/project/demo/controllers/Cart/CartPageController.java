package project.demo.controllers.Cart;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.demo.controllers.Main.MainStructureController;
import project.demo.models.CartItem;
import project.demo.models.CartManager;

import java.io.IOException;
import java.util.Map;

public class CartPageController {

    @FXML
    private AnchorPane receiptPane; // Reference to the receipt pane

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

    private static CartPageController instance;

    @FXML
    private Button removeCouponButton;

    @FXML
    private TextField promoCodeField;

    private final ObservableList<CartItem> cartItems = CartManager.getInstance().getCartItems();
    private final SimpleDoubleProperty subtotal = new SimpleDoubleProperty(0.0);
    private final SimpleDoubleProperty shippingFee = new SimpleDoubleProperty(0.0);
    private final SimpleDoubleProperty couponDiscount = new SimpleDoubleProperty(0.0);

    // Promo codes map
    private final Map<String, Double> promoCodes = Map.of(
            "SAVE10", 10.0, "FREESHIP", 0.0, "DISCOUNT20", 20.0,
            "SAVE5", 5.0, "BLACKFRIDAY", 15.0, "CYBERMONDAY", 20.0,
            "WELCOME", 10.0, "WINTER30", 0.3, "WINTER40", 0.4, "WINTER50", 0.5
    );

    public AnchorPane getContentPane() {
        return contentPane;
    }

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

        // Listen for changes in the cart item list and recalculate subtotal
        cartItems.addListener((ListChangeListener<CartItem>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(item ->
                            item.quantityProperty().addListener((observable, oldValue, newValue) -> recalculateSubtotal())
                    );
                }
                recalculateSubtotal();
            }
        });

        // Load initial cart table view
        loadView("/project/demo/FXMLCartPage/CartTable.fxml");

        // Set up quantity listeners for existing items
        cartItems.forEach(item ->
                item.quantityProperty().addListener((observable, oldValue, newValue) -> recalculateSubtotal())
        );

        recalculateSubtotal();
    }

    public CartPageController() {
        instance = this;
    }

    public static CartPageController getInstance() {
        return instance;
    }

    public void updateShippingFee(double fee) {
        System.out.println("[DEBUG] Shipping fee updated to: " + fee);
        shippingFee.set(fee);
        recalculateTotal();
    }

    public void recalculateSubtotal() {
        double total = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        subtotal.set(total);

        // Apply the coupon discount if applicable
        if (appliedCoupon != null && promoCodes.containsKey(appliedCoupon)) {
            double discount = promoCodes.get(appliedCoupon);
            if (discount < 1.0) {
                couponDiscount.set(subtotal.get() * discount); // Percentage discount
            } else {
                couponDiscount.set(Math.min(discount, subtotal.get())); // Fixed amount discount
            }
        } else {
            couponDiscount.set(0.0);
        }
    }

    private void recalculateTotal() {
        double totalAmount = subtotal.get() - couponDiscount.get() + shippingFee.get();
        System.out.println("[DEBUG] Total recalculated: " + totalAmount);
    }

    @FXML
    public void applyPromoCode() {
        String promoCode = promoCodeField.getText().toUpperCase();
        if (promoCodes.containsKey(promoCode)) {
            appliedCoupon = promoCode;
            recalculateSubtotal();

            appliedCouponLabel.setText("Applied: " + promoCode);
            removeCouponButton.setVisible(true);

            System.out.println("Promo code applied: " + promoCode);
        } else {
            System.err.println("[ERROR] Invalid promo code: " + promoCode);
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

            // Get the controller for the new view and set the main controller
            Object controller = loader.getController();

            if (controller instanceof CartTableController cartTableController) {
                cartTableController.setMainController(this);
            } else if (controller instanceof DetailsController detailsController) {
                detailsController.setMainController(this);
            } else if (controller instanceof ShippingController shippingController) {
                shippingController.setMainController(this);
                shippingController.setShippingFeeListener(shippingFee);
            } else if (controller instanceof PaymentController paymentController) {
                paymentController.setMainController(this);
            } else if (controller instanceof PaymentSuccessController successController) {
                successController.setMainController(this);
            }

            contentPane.getChildren().clear(); // Clear current view
            contentPane.getChildren().add(newView); // Add the new view

        } catch (IOException e) {
            System.err.println("[ERROR] Failed to load FXML file: " + fxmlPath);
            e.printStackTrace();
        }
    }

    public void hideReceiptPane() {
        if (receiptPane != null) {
            receiptPane.setVisible(false);
            System.out.println("[INFO] Receipt pane hidden.");
        } else {
            System.err.println("[ERROR] Receipt pane is null.");
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

    public void startNavigatingWithLoader(String targetFxmlPath, String gifKey) {
        try {
            // Load the LoadingPageCart.fxml as the loading screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/demo/FXMLCartPage/LoadingPageCart.fxml"));
            Parent loadingView = loader.load();

            // Get the controller for the loading page
            LoadingPageCartController loadingController = loader.getController();

            // Initialize the loading screen and provide it with the target FXML and GIF key
            loadingController.initializeLoading(contentPane, targetFxmlPath, gifKey);

            // Display the loading screen in the contentPane
            contentPane.getChildren().setAll(loadingView);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] Failed to load LoadingPageCart.fxml.");
        }
    }


    public void goToCart() {
        startNavigatingWithLoader("/project/demo/FXMLCartPage/CartTable.fxml", "loadingCart");
    }

    public void goToPayment() {
        startNavigatingWithLoader("/project/demo/FXMLCartPage/Payment.fxml", "loadingPayment");
    }

    public void goToDetails() {
        startNavigatingWithLoader("/project/demo/FXMLCartPage/Details.fxml", "loadingDetails");
    }

    public void goToShipping() {
        startNavigatingWithLoader("/project/demo/FXMLCartPage/Shipping.fxml", "loadingShipping");
    }

    public double getSubtotal() {
        return subtotal.get();
    }

    public double getCouponDiscount() {
        return couponDiscount.get();
    }

}