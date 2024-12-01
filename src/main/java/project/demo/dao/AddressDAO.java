package project.demo.dao;

import project.demo.models.Address;
import java.util.List;

public interface AddressDAO {
    void addAddress(Address address);
    void updateAddress(Address address);
    void deleteAddress(int id);
    List<Address> getAddressesByUserId(int userId);
    Address getAddressById(int id);
}
