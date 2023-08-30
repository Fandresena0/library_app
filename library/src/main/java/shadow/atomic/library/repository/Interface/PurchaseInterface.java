package shadow.atomic.library.repository.Interface;

import shadow.atomic.library.model.purchase;

import java.sql.SQLException;
import java.util.List;

public interface PurchaseInterface {
    void insertPurchase(purchase toInsert) throws SQLException;
    List<purchase> findAllPurchase() throws SQLException;
    List<purchase> findPurchaseById(int id) throws SQLException;
    void deletePurchaseById(int id) throws SQLException;
}
