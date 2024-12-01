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
        try (Connection connection = db.getConnection()) {
            String query = "INSERT INTO addresses (user_id, address_type, street, barangay, city, province, postal_code) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, address.getUserId());
            statement.setString(2, address.getType()); // Changed to address_type
            statement.setString(3, address.getStreet());
            statement.setString(4, address.getBarangay());
            statement.setString(5, address.getCity());
            statement.setString(6, address.getProvince());
            statement.setString(7, address.getPostalCode());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAddress(Address address) {
        try (Connection connection = db.getConnection()) {
            String query = "UPDATE addresses SET address_type = ?, street = ?, barangay = ?, city = ?, province = ?, postal_code = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, address.getType()); // Changed to address_type
            statement.setString(2, address.getStreet());
            statement.setString(3, address.getBarangay());
            statement.setString(4, address.getCity());
            statement.setString(5, address.getProvince());
            statement.setString(6, address.getPostalCode());
            statement.setInt(7, address.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAddress(int id) {
        try (Connection connection = db.getConnection()) {
            String query = "DELETE FROM addresses WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Address> getAddressesByUserId(int userId) {
        List<Address> addresses = new ArrayList<>();
        try (Connection connection = db.getConnection()) {
            String query = "SELECT * FROM addresses WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Address address = new Address();
                address.setId(resultSet.getInt("id"));
                address.setUserId(resultSet.getInt("user_id"));
                address.setType(resultSet.getString("address_type")); // Changed to address_type
                address.setStreet(resultSet.getString("street"));
                address.setBarangay(resultSet.getString("barangay"));
                address.setCity(resultSet.getString("city"));
                address.setProvince(resultSet.getString("province"));
                address.setPostalCode(resultSet.getString("postal_code"));
                addresses.add(address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addresses;
    }

    @Override
    public Address getAddressById(int id) {
        try (Connection connection = db.getConnection()) {
            String query = "SELECT * FROM addresses WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Address address = new Address();
                address.setId(resultSet.getInt("id"));
                address.setUserId(resultSet.getInt("user_id"));
                address.setType(resultSet.getString("address_type")); // Changed to address_type
                address.setStreet(resultSet.getString("street"));
                address.setBarangay(resultSet.getString("barangay"));
                address.setCity(resultSet.getString("city"));
                address.setProvince(resultSet.getString("province"));
                address.setPostalCode(resultSet.getString("postal_code"));
                return address;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
