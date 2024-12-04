package project.demo.models;

public class PayPal {
    private int id;
    private int userId;
    private String paypalEmail;
    private String alternateEmail;

    // Constructor with all fields
    public PayPal(int id, int userId, String paypalEmail, String alternateEmail) {
        this.id = id;
        this.userId = userId;
        this.paypalEmail = paypalEmail;
        this.alternateEmail = alternateEmail;
    }

    // Constructor without ID (for new records)
    public PayPal(int userId, String paypalEmail, String alternateEmail) {
        this.userId = userId;
        this.paypalEmail = paypalEmail;
        this.alternateEmail = alternateEmail;
    }

    // Default constructor
    public PayPal() {
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

    public String getPaypalEmail() {
        return paypalEmail;
    }

    public void setPaypalEmail(String paypalEmail) {
        this.paypalEmail = paypalEmail;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }
}
