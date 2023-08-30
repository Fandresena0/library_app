package shadow.atomic.library.service;

import org.springframework.stereotype.Service;
import shadow.atomic.library.model.purchase;
import shadow.atomic.library.repository.DAO.PurchaseDAO;

import java.sql.SQLException;
import java.util.List;

@Service
public class PurchaseService {
    private PurchaseDAO purchaseDAO;

    public PurchaseService(PurchaseDAO purchaseDAO){
        this.purchaseDAO = purchaseDAO;
    }

    public List<purchase> findAllPurchase(){
        try {
            return purchaseDAO.findAllPurchase();
        } catch (SQLException e) {
            throw new RuntimeException("There has been an error when fetching all Purchases");
        }
    }

    public List<purchase> findPurchaseById(int id){
        try{
            return purchaseDAO.findPurchaseById(id);
        }catch(SQLException e){
            throw new RuntimeException("There has been an error when fetching Purchase with id : "+id);
        }
    }

    public purchase insertPurchase(purchase toInsert){
        try{
            this.purchaseDAO.insertPurchase(toInsert);

            return toInsert;
        }catch(SQLException e){
            throw new RuntimeException("There was an error when inserting the Purchase.");
        }
    }

    public void deletePurchase(int idPurchase){
        try {
            this.purchaseDAO.deletePurchaseById(idPurchase);
        }catch (SQLException e){
            throw new RuntimeException("error in delete action");
        }
    }
}
