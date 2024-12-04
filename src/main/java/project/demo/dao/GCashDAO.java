package project.demo.dao;

import project.demo.models.GCash;

public interface GCashDAO {
    boolean saveOrUpdateGCashAccount(GCash gcash);
    GCash getGCashByUserId(int userId);
}
