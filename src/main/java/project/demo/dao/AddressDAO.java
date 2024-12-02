package project.demo.dao;

import project.demo.models.Address;
import java.util.List;

public interface AddressDAO {
    void addAddress(Address address);
    void updateAddress(Address address);
    boolean deleteAddress(int id);
    List<Address> getAddressesByUserId(int userId);
    Address getAddressById(int id);

    void saveOrUpdateAddress(Address address);;
}
