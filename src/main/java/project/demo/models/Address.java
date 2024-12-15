package project.demo.models;

public class Address {
    private int id;
    private int userId;
    private String type; // e.g., Home, Work
    private String street;
    private String barangay;
    private String city;
    private String province;
    private String postalCode;
    private String region;
    // Constructor
    public Address() {}

    public Address(int id, String type, String street, String city, String postalCode, String province, String region) {
        this.id = id;
        this.type = type;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.province = province;
        this.region = region;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    // Method to combine the address into a single string
    public String getFullAddress() {
        return String.format("%s, %s, %s, %s, Postal Code: %s",
                street, city, province, region, postalCode);
    }
}
