package project.demo.models;

public class PayPal extends PaymentMethod {
    private String email;
    private String alternateEmail;

    public PayPal(int id, int userId, String email, String alternateEmail) {
        super(id, userId, "PayPal");
        this.email = email;
        this.alternateEmail = alternateEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public String getDetails() {
        return "PayPal: " + email + ", Alternate: " + alternateEmail;
    }
}
