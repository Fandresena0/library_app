package shadow.atomic.library.repository.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import shadow.atomic.library.ConnectionDatabase.DatabaseConfig;
import shadow.atomic.library.model.book;
import shadow.atomic.library.repository.Interface.BookInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDAO implements BookInterface {
    private DatabaseConfig databaseConfig;
    @Autowired
    public BookDAO(DatabaseConfig databaseConfig){
        this.databaseConfig = databaseConfig;
    }

    private void convertToList(List<book> allbooks, ResultSet result) throws SQLException {
        allbooks.add(new book(
                result.getInt("id_book"),
                result.getString("title"),
                result.getString("description"),
                result.getString("autor")
        ));
    }

    @Override
    public void insertBook(book toInsert) throws SQLException {
        String sql = "INSERT INTO book(id_book,title,description,autor) " +
                "VALUES (? , ? , ? , ?)";

        try (PreparedStatement statement = databaseConfig.dataSource().getConnection().prepareStatement(sql)) {
            statement.setInt(1, toInsert.getIdBook());
            statement.setString(2, toInsert.getTitle());
            statement.setString(3, toInsert.getDescription());
            statement.setString(4, toInsert.getAutor());

            statement.executeUpdate();
        }
    }

    @Override
    public List<book> findAllBook() throws SQLException {
        List<book> allBooks = new ArrayList<>();
        String sql = "SELECT * FROM book";

        try(PreparedStatement preparedStatement = databaseConfig.dataSource().getConnection().prepareStatement(sql)){
            ResultSet result = preparedStatement.executeQuery();

            while(result.next()){
                convertToList(allBooks, result);
            }
        }

        return allBooks;
    }

    @Override
    public List<book> findBookById(int id) throws SQLException {
        List<book> allbooks = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE id_book= "+id;

        try(PreparedStatement preparedStatement = databaseConfig.dataSource().getConnection().prepareStatement(sql)) {
            try (ResultSet result = preparedStatement.executeQuery()) {

                while (result.next()) {
                    convertToList(allbooks, result);
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allbooks;
    }

    @Override
    public void deleteBookById(int idBook) throws SQLException {
        String SQLDeleteFromToBuy = "DELETE FROM to_buy WHERE id_book = ?";
        String SQLDeleteFromToLend = "DELETE FROM to_lend WHERE id_book = ?";
        String SQLDeleteFromBook = "DELETE FROM book WHERE id_book = ?";

        try (Connection connection = databaseConfig.dataSource().getConnection();
             PreparedStatement deleteFromToBuyStatement = connection.prepareStatement(SQLDeleteFromToBuy);
             PreparedStatement deleteFromToLendStatement = connection.prepareStatement(SQLDeleteFromToLend);
             PreparedStatement deleteFromBookStatement = connection.prepareStatement(SQLDeleteFromBook)) {

            // Supprimer les enregistrements liés dans la table "to_buy"
            deleteFromToBuyStatement.setInt(1, idBook);
            deleteFromToBuyStatement.executeUpdate();

            // supprimer les enregistrements liés dans la table "to_lend"
            deleteFromToLendStatement.setInt(1, idBook);
            deleteFromToLendStatement.executeUpdate();

            // supprimer l'enregistrement de la table "book"
            deleteFromBookStatement.setInt(1, idBook);
            deleteFromBookStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void UpdateBookById(book toUpdate) throws SQLException{
        String sql = "UPDATE book SET title = ?, description = ? , autor = ? WHERE id_book = ?";
        try (PreparedStatement preparedStatement = databaseConfig.dataSource().getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, toUpdate.getTitle());
            preparedStatement.setString(2, toUpdate.getDescription());
            preparedStatement.setString(3, toUpdate.getAutor());
            preparedStatement.setInt(4, toUpdate.getIdBook());
            preparedStatement.executeUpdate();
        }
    }

}