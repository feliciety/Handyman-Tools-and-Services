package project.demo.models;

public class ShippingDetails {

    private static ShippingDetails instance;

    private String contact = "";
    private String address = "";

    private ShippingDetails() {}

    public static ShippingDetails getInstance() {
        if (instance == null) {
            instance = new ShippingDetails();
        }
        return instance;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
