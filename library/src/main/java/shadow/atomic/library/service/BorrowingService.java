package shadow.atomic.library.service;

import org.springframework.stereotype.Repository;
import shadow.atomic.library.model.borrowing;
import shadow.atomic.library.repository.DAO.BorrowingDAO;

import java.sql.SQLException;
import java.util.List;

@Repository
public class BorrowingService {
    private BorrowingDAO BorrowingDAO;

    public BorrowingService(BorrowingDAO BorrowingDAO){
        this.BorrowingDAO = BorrowingDAO;
    }

    public List<borrowing> findAllBorrowing(){
        try {
            return BorrowingDAO.findAllBorrowing();
        } catch (SQLException e) {
            throw new RuntimeException("There has been an error when fetching all Borrowings");
        }
    }

    public List<borrowing> findBorrowingById(int id){
        try{
            return BorrowingDAO.findBorrowingById(id);
        }catch(SQLException e){
            throw new RuntimeException("There has been an error when fetching Borrowing with id : "+id);
        }
    }

    public borrowing insertBorrowing(borrowing toInsert){
        try{
            this.BorrowingDAO.insertBorrowing(toInsert);

            return toInsert;
        }catch(SQLException e){
            throw new RuntimeException("There was an error when inserting the Borrowing.");
        }
    }

    public void deleteBorrowing(int idBorrowing){
        try {
            this.BorrowingDAO.deleteBorrowingById(idBorrowing);
        }catch (SQLException e){
            throw new RuntimeException("error in delete action");
        }
    }
}
