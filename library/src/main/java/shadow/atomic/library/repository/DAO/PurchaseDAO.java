package shadow.atomic.library.repository.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import shadow.atomic.library.ConnectionDatabase.DatabaseConfig;
import shadow.atomic.library.model.purchase;
import shadow.atomic.library.repository.Interface.PurchaseInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PurchaseDAO implements PurchaseInterface {
    private DatabaseConfig databaseConfig;
    @Autowired
    public PurchaseDAO(DatabaseConfig databaseConfig){
        this.databaseConfig = databaseConfig;
    }
    private void convertToList(List<purchase> allPurchases, ResultSet result) throws SQLException {
        allPurchases.add(new purchase(
                result.getInt("id_purchase"),
                result.getDate("date_of_purchase"),
                result.getInt("price"),
                result.getInt("id_client")
        ));
    }

    @Override
    public void insertPurchase(purchase toInsert) throws SQLException {
        String sql = "INSERT INTO purchase(id_purchase,date_of_purchase,price,id_client) " +
                "VALUES (? , ? ,?, ?)";

        try (PreparedStatement statement = databaseConfig.dataSource().getConnection().prepareStatement(sql)) {
            statement.setInt(1, toInsert.getIdPurchase());
            statement.setDate(2, toInsert.getDateOfPurchase());
            statement.setInt(3,toInsert.getPrice());
            statement.setInt(4, toInsert.getIdClient());

            statement.executeUpdate();
        }
    }

    @Override
    public List<purchase> findAllPurchase() throws SQLException {
        List<purchase> allPurchases = new ArrayList<>();
        String sql = "SELECT * FROM purchase";

        try(PreparedStatement preparedStatement = databaseConfig.dataSource().getConnection().prepareStatement(sql)){
            ResultSet result = preparedStatement.executeQuery();

            while(result.next()){
                convertToList(allPurchases, result);
            }
        }

        return allPurchases;
    }

    @Override
    public List<purchase> findPurchaseById(int id) throws SQLException {
        List<purchase> allPurchases = new ArrayList<>();
        String sql = "SELECT * FROM purchase WHERE id_purchase= "+id;

        try(PreparedStatement preparedStatement = databaseConfig.dataSource().getConnection().prepareStatement(sql)) {
            try (ResultSet result = preparedStatement.executeQuery()) {

                while (result.next()) {
                    convertToList(allPurchases, result);
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allPurchases;
    }

    @Override
    public void deletePurchaseById(int idPurchase) throws SQLException {
        String SQLDeleteFromToBuy = "DELETE FROM to_buy WHERE id_purchase = ?";
        String SQLDeleteFromPurchase = "DELETE FROM purchase WHERE id_purchase = ?";

        try (Connection connection = databaseConfig.dataSource().getConnection();
             PreparedStatement deleteFromToBuyStatement = connection.prepareStatement(SQLDeleteFromToBuy);
             PreparedStatement deleteFromPurchaseStatement = connection.prepareStatement(SQLDeleteFromPurchase)) {

            // Supprimer les enregistrements liés dans la table "to_buy"
            deleteFromToBuyStatement.setInt(1, idPurchase);
            deleteFromToBuyStatement.executeUpdate();

            // supprimer l'enregistrement de la table "Purchase"
            deleteFromPurchaseStatement.setInt(1, idPurchase);
            deleteFromPurchaseStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
