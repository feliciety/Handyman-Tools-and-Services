package project.demo.models;

public abstract class PaymentMethod {
    private int id;
    private int userId;
    private String paymentMethodType;

    // Constructor
    public PaymentMethod(int id, int userId, String paymentMethodType) {
        this.id = id;
        this.userId = userId;
        this.paymentMethodType = paymentMethodType;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(String paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public abstract String getDetails();
}
