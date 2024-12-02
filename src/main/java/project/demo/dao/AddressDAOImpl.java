package project.demo.dao;

import project.demo.models.Address;
import project.demo.DataBase.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AddressDAOImpl implements AddressDAO {
    private final DatabaseConfig db = new DatabaseConfig();

    @Override
    public void addAddress(Address address) {
        String query = "INSERT INTO addresses (user_id, address_type, street, barangay, city, province, postal_code, region) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, address.getUserId());
            statement.setString(2, address.getType());
            statement.setString(3, address.getStreet());
            statement.setString(4, address.getBarangay());
            statement.setString(5, address.getCity());
            statement.setString(6, address.getProvince());
            statement.setString(7, address.getPostalCode());
            statement.setString(8, address.getRegion()); // Include the region

            statement.executeUpdate();
            System.out.println("[INFO] Address added successfully.");
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to add address: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void updateAddress(Address address) {
        String query = "UPDATE addresses SET address_type = ?, street = ?, barangay = ?, city = ?, province = ?, postal_code = ?, region = ? WHERE id = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, address.getType());
            statement.setString(2, address.getStreet());
            statement.setString(3, address.getBarangay());
            statement.setString(4, address.getCity());
            statement.setString(5, address.getProvince());
            statement.setString(6, address.getPostalCode());
            statement.setString(7, address.getRegion()); // Include the region
            statement.setInt(8, address.getId());

            statement.executeUpdate();
            System.out.println("[INFO] Address updated successfully.");
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to update address: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteAddress(int id) {
        String query = "DELETE FROM addresses WHERE id = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("[INFO] Address deleted successfully.");
                return true;
            } else {
                System.err.println("[WARNING] No address found with ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to delete address: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Address> getAddressesByUserId(int userId) {
        List<Address> addresses = new ArrayList<>();
        String query = "SELECT * FROM addresses WHERE user_id = ?";

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Address address = new Address();
                address.setId(resultSet.getInt("id"));
                address.setUserId(resultSet.getInt("user_id"));
                address.setType(resultSet.getString("address_type"));
                address.setStreet(resultSet.getString("street"));
                address.setBarangay(resultSet.getString("barangay"));
                address.setCity(resultSet.getString("city"));
                address.setProvince(resultSet.getString("province"));
                address.setPostalCode(resultSet.getString("postal_code"));
                address.setRegion(resultSet.getString("region")); // Include the region

                addresses.add(address);
            }
            System.out.println("[INFO] Retrieved " + addresses.size() + " addresses for user ID: " + userId);
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to retrieve addresses: " + e.getMessage());
            e.printStackTrace();
        }

        return addresses;
    }

    @Override
    public Address getAddressById(int id) {
        String query = "SELECT * FROM addresses WHERE id = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Address address = new Address();
                address.setId(resultSet.getInt("id"));
                address.setUserId(resultSet.getInt("user_id"));
                address.setType(resultSet.getString("address_type"));
                address.setStreet(resultSet.getString("street"));
                address.setBarangay(resultSet.getString("barangay"));
                address.setCity(resultSet.getString("city"));
                address.setProvince(resultSet.getString("province"));
                address.setPostalCode(resultSet.getString("postal_code"));
                address.setRegion(resultSet.getString("region")); // Include the region

                System.out.println("[INFO] Address retrieved successfully for ID: " + id);
                return address;
            } else {
                System.err.println("[WARNING] No address found with ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to retrieve address: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void saveOrUpdateAddress(Address address) {
        if (address.getId() > 0) {
            // If the address ID exists, update the address
            updateAddress(address);
        } else {
            // If the address ID does not exist, add a new address
            addAddress(address);
        }
    }
}
