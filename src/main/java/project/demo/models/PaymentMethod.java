package project.demo.models;

public abstract class PaymentMethod {
    private int paymentMethodId;   // A unique identifier for the payment method
    private String paymentMethodType;   // E.g., "Credit Card", "PayPal", etc.

    // Constructor
    public PaymentMethod(int paymentMethodId, String paymentMethodType) {
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodType = paymentMethodType;
    }

    // Getters and setters
    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(String paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    // Abstract method to be implemented by subclasses to return details
    public abstract String getDetails();

    public int getId() {
        return getPaymentMethodId();  // This calls the getPaymentMethodId from the current class
    }
}
