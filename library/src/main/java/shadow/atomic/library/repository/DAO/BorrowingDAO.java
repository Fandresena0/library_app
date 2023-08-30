package shadow.atomic.library.repository.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import shadow.atomic.library.ConnectionDatabase.DatabaseConfig;
import shadow.atomic.library.model.borrowing;
import shadow.atomic.library.repository.Interface.BorrowingInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BorrowingDAO implements BorrowingInterface {

    private DatabaseConfig databaseConfig;
    @Autowired
    public BorrowingDAO(DatabaseConfig databaseConfig){
        this.databaseConfig = databaseConfig;
    }
    private void convertToList(List<borrowing> allBorrowing, ResultSet result) throws SQLException {
        allBorrowing.add(new borrowing(
                result.getInt("id_borrowing"),
                result.getDate("loan_date"),
                result.getDate("return_date"),
                result.getInt("id_client")
        ));
    }

    @Override
    public void insertBorrowing(borrowing toInsert) throws SQLException {
        String sql = "INSERT INTO borrowing(id_borrowing,loan_date,return_date,id_client) " +
                "VALUES (? , ? , ? ,?)";

        try (PreparedStatement statement = databaseConfig.dataSource().getConnection().prepareStatement(sql)) {
            statement.setInt(1, toInsert.getIdBorrowing());
            statement.setDate(2, toInsert.getLoanDate());
            statement.setDate(3, toInsert.getReturnDate());
            statement.setInt(4,toInsert.getIdClient());

            statement.executeUpdate();
        }
    }

    @Override
    public List<borrowing> findAllBorrowing() throws SQLException {
        List<borrowing> allBorrowing = new ArrayList<>();
        String sql = "SELECT * FROM borrowing";

        try(PreparedStatement preparedStatement = databaseConfig.dataSource().getConnection().prepareStatement(sql)){
            ResultSet result = preparedStatement.executeQuery();

            while(result.next()){
                convertToList(allBorrowing, result);
            }
        }

        return allBorrowing;
    }

    @Override
    public List<borrowing> findBorrowingById(int id) throws SQLException {
        List<borrowing> allBorrowing = new ArrayList<>();
        String sql = "SELECT * FROM borrowing WHERE id_borrowing = "+id;

        try(PreparedStatement preparedStatement = databaseConfig.dataSource().getConnection().prepareStatement(sql)) {
            try (ResultSet result = preparedStatement.executeQuery()) {

                while (result.next()) {
                    convertToList(allBorrowing, result);
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allBorrowing;
    }

    @Override
    public void deleteBorrowingById(int id) throws SQLException {
        String SQLDeleteFromToLend = "DELETE FROM to_lend WHERE id_borrowing = ?";
        String SQLDeleteFromBorrowing = "DELETE FROM borrowing WHERE id_borrowing = ?";

        try (Connection connection = databaseConfig.dataSource().getConnection();
             PreparedStatement deleteFromToLendStatement = connection.prepareStatement(SQLDeleteFromToLend);
             PreparedStatement deleteFromBorrowingStatement = connection.prepareStatement(SQLDeleteFromBorrowing)) {

            // Supprimer les enregistrements liés dans la table "to_lend"
            deleteFromToLendStatement.setInt(1, id);
            deleteFromToLendStatement.executeUpdate();

            // supprimer l'enregistrement de la table "Borrowing"
            deleteFromBorrowingStatement.setInt(1, id);
            deleteFromBorrowingStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
