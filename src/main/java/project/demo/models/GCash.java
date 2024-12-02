package project.demo.models;

public class GCash extends PaymentMethod {
    private String phoneNumber;
    private String accountNumber;

    public GCash(int id, int userId, String phoneNumber, String accountNumber) {
        super(id, userId, "GCash");
        this.phoneNumber = phoneNumber;
        this.accountNumber = accountNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getDetails() {
        return "GCash: " + phoneNumber + ", Account: " + accountNumber;
    }
}
