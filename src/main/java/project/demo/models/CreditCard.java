package project.demo.models;

public class CreditCard {
    private int id;
    private int userId;
    private String cardName;
    private String cardNumber;
    private String cvv;
    private String expiryDate;
    private String billingAddress;
    private String zipCode;

    public CreditCard() {
    }

    public CreditCard(int id, int userId, String cardName, String cardNumber, String cvv, String expiryDate, String billingAddress, String zipCode) {
        this.id = id;
        this.userId = userId;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
        this.billingAddress = billingAddress;
        this.zipCode = zipCode;
    }


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

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setExpiry(String expiryDate) {
        this.expiryDate = expiryDate;
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

    public String getExpiry() {
        return expiryDate;
    }
}
