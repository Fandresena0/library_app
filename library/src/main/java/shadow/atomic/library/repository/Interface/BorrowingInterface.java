package shadow.atomic.library.repository.Interface;

import shadow.atomic.library.model.borrowing;

import java.sql.SQLException;
import java.util.List;

public interface BorrowingInterface {
    void insertBorrowing(borrowing toInsert) throws SQLException;
    List<borrowing> findAllBorrowing() throws SQLException;
    List<borrowing> findBorrowingById(int id) throws SQLException;
    void deleteBorrowingById(int id) throws SQLException;
}
