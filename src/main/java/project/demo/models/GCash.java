package project.demo.models;

import javafx.scene.control.TextField;

public class GCash {
    private int id;
    private int userId;
    private String accountName;
    private String phoneNumber;


    public GCash(int id, int userId, String accountName, String phoneNumber) {
        this.id = id;
        this.userId = userId;
        this.accountName = accountName;
        this.phoneNumber = phoneNumber;
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

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
