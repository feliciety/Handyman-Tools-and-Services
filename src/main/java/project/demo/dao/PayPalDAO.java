package project.demo.dao;

import project.demo.models.PayPal;

public interface PayPalDAO {
    boolean saveOrUpdatePayPalAccount(PayPal payPal);
    PayPal getPayPalByUserId(int userId);

}
