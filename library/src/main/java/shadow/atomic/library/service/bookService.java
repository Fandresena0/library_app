package shadow.atomic.library.service;

import org.springframework.stereotype.Service;
import shadow.atomic.library.model.book;
import shadow.atomic.library.repository.DAO.BookDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class bookService {
    private BookDAO bookDAO;

    public bookService(BookDAO bookDAO){
        this.bookDAO = bookDAO;
    }

    public List<book> findAllbooks(){
        try {
            return bookDAO.findAllBook();
        } catch (SQLException e) {
            throw new RuntimeException("There has been an error when fetching all books");
        }
    }

    public List<book> findbookById(int id){
        try{
            return bookDAO.findBookById(id);
        }catch(SQLException e){
            throw new RuntimeException("There has been an error when fetching book with id : "+id);
        }
    }

    public book insertBook(book toInsert){
        try{
            this.bookDAO.insertBook(toInsert);

            return toInsert;
        }catch(SQLException e){
            throw new RuntimeException("There was an error when inserting the book.");
        }
    }

    public void deleteBook(int idBook){
        try {
            this.bookDAO.deleteBookById(idBook);
        }catch (SQLException e){
            throw new RuntimeException("error in delete action");
        }
    }

    public book updateBook(book toUpdate){
        try {
            this.bookDAO.UpdateBookById(toUpdate);

            return toUpdate;
        }catch (SQLException e){
            throw new RuntimeException("There was an error when updating the book.");
        }
    }
}