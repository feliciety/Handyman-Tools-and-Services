package project.demo.dao;

import project.demo.models.CreditCard;

public interface CreditCardDAO {
    boolean saveOrUpdateCreditCardAccount(CreditCard creditCard);
    CreditCard getCreditCardByUserId(int userId);
}
