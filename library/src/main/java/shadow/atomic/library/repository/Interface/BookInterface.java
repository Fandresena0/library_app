package shadow.atomic.library.repository.Interface;


import shadow.atomic.library.model.book;

import java.sql.SQLException;
import java.util.List;

public interface BookInterface {
    void insertBook(book toInsert) throws SQLException;
    List<book> findAllBook() throws SQLException;
    List<book> findBookById(int id) throws SQLException;
    void deleteBookById(int idBook) throws SQLException;

    void UpdateBookById(book toUpdate) throws SQLException;


}
