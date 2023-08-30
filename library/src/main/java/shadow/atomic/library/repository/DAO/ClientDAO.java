package shadow.atomic.library.repository.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import shadow.atomic.library.ConnectionDatabase.DatabaseConfig;
import shadow.atomic.library.model.client;
import shadow.atomic.library.repository.Interface.ClientInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientDAO implements ClientInterface {
    private DatabaseConfig databaseConfig;
    @Autowired
    public ClientDAO(DatabaseConfig databaseConfig){
        this.databaseConfig = databaseConfig;
    }

    private void convertToList(List<client> allClients, ResultSet result) throws SQLException {
        allClients.add(new client(
                result.getInt("id_client"),
                result.getString("first_name"),
                result.getString("last_name"),
                result.getString("email"),
                result.getString("phone"),
                result.getString("cin")
        ));
    }
    @Override
    public void insertClient(client toInsert) throws SQLException {
        String sql = "INSERT INTO client(id_client,first_name,last_name,email,phone,cin) " +
                "VALUES (? , ? , ? , ?, ? , ?)";

        try (PreparedStatement statement = databaseConfig.dataSource().getConnection().prepareStatement(sql)) {
            statement.setInt(1, toInsert.getIdClient());
            statement.setString(2, toInsert.getFirstName());
            statement.setString(3, toInsert.getLastName());
            statement.setString(4, toInsert.getEmail());
            statement.setString(5,toInsert.getPhone());
            statement.setString(6,toInsert.getCIN());

            statement.executeUpdate();
        }
    }

    @Override
    public List<client> findAllClient() throws SQLException {
        List<client> allClients = new ArrayList<>();
        String sql = "SELECT * FROM client";

        try(PreparedStatement preparedStatement = databaseConfig.dataSource().getConnection().prepareStatement(sql)){
            ResultSet result = preparedStatement.executeQuery();

            while(result.next()){
                convertToList(allClients, result);
            }
        }

        return allClients;
    }

    @Override
    public List<client> findClientById(int id) throws SQLException {
        List<client> allClients = new ArrayList<>();
        String sql = "SELECT * FROM client WHERE id_client= "+id;

        try(PreparedStatement preparedStatement = databaseConfig.dataSource().getConnection().prepareStatement(sql)) {
            try (ResultSet result = preparedStatement.executeQuery()) {

                while (result.next()) {
                    convertToList(allClients, result);
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allClients;
    }

    @Override
    public void deleteClientById(int idClient) throws SQLException {
        String SQLDeleteFromToBuy = "DELETE FROM to_buy WHERE id_purchase IN (SELECT id_purchase FROM purchase WHERE id_client = ?)";
        String SQLDeleteFromPurchase = "DELETE FROM purchase WHERE id_client = ?";
        String SQLDeleteFromBorrowing = "DELETE FROM borrowing WHERE id_client = ?";
        String SQLDeleteFromClient = "DELETE FROM client WHERE id_client = ?";

        try (Connection connection = databaseConfig.dataSource().getConnection();
             PreparedStatement deleteFromToBuyStatement = connection.prepareStatement(SQLDeleteFromToBuy);
             PreparedStatement deleteFromPurchaseStatement = connection.prepareStatement(SQLDeleteFromPurchase);
             PreparedStatement deleteFromBorrowingStatement = connection.prepareStatement(SQLDeleteFromBorrowing);
             PreparedStatement deleteFromClientStatement = connection.prepareStatement(SQLDeleteFromClient)) {

            connection.setAutoCommit(false); // Désactiver l'autocommit pour gérer la transaction

            // Supprimer les enregistrements liés dans la table "to_buy"
            deleteFromToBuyStatement.setInt(1, idClient);
            deleteFromToBuyStatement.executeUpdate();

            // supprimer les enregistrements liés dans la table "purchase"
            deleteFromPurchaseStatement.setInt(1, idClient);
            deleteFromPurchaseStatement.executeUpdate();

            // Supprimer les enregistrements liés dans la table "borrowing"
            deleteFromBorrowingStatement.setInt(1, idClient);
            deleteFromBorrowingStatement.executeUpdate();

            // supprimer l'enregistrement de la table "client"
            deleteFromClientStatement.setInt(1, idClient);
            deleteFromClientStatement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rejeter l'exception pour la gérer plus haut si nécessaire
        }
    }
    @Override
    public void UpdateClientById(client toUpdate) throws SQLException{
        String sql = "UPDATE client SET first_name = ?, last_name = ? , email = ? , phone = ? , cin = ? WHERE id_client = ?";
        try (PreparedStatement preparedStatement = databaseConfig.dataSource().getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, toUpdate.getFirstName());
            preparedStatement.setString(2, toUpdate.getLastName());
            preparedStatement.setString(3, toUpdate.getEmail());
            preparedStatement.setString(4, toUpdate.getPhone());
            preparedStatement.setString(5,toUpdate.getCIN());
            preparedStatement.setInt(6,toUpdate.getIdClient());
            preparedStatement.executeUpdate();
        }
    }



}
