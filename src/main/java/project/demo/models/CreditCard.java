package project.demo.models;

public class CreditCard extends PaymentMethod {
    private String nameOnCard;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String billingAddress;
    private String zipCode;

    // Constructor
    public CreditCard(int id, int userId, String nameOnCard, String cardNumber, String expiryDate, String cvv, String billingAddress, String zipCode) {
        super(id, userId, "Credit Card");
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.billingAddress = billingAddress;
        this.zipCode = zipCode;
    }

    // Getters and Setters
    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDetails() {
        return "Credit Card: " + nameOnCard + ", " + cardNumber + ", " + expiryDate;
    }
}
