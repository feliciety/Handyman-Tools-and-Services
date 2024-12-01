package project.demo.models;

public class Address {

    private int id; // Address ID
    private int userId; // User ID associated with this address
    private String type; // Address type (e.g., "Work" or "Home")
    private String street; // Street information
    private String barangay; // Barangay information
    private String city; // City information
    private String province; // Province information
    private String region; // Region information
    private String postalCode; // Postal code

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Concatenates the street, barangay, and city fields to create a compact address for display.
     *
     * @return A formatted string representing the compact address.
     */
    public String getCompactAddress() {
        return String.format("%s, %s, %s", street, barangay, city);
    }

    /**
     * Concatenates the province and region fields to create a regional address for display.
     *
     * @return A formatted string representing the regional address.
     */
    public String getRegionalAddress() {
        return String.format("%s, %s", province, region);
    }
}
