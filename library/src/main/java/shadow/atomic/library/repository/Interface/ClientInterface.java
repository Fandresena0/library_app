package shadow.atomic.library.repository.Interface;

import shadow.atomic.library.model.book;
import shadow.atomic.library.model.client;

import java.sql.SQLException;
import java.util.List;

public interface ClientInterface {
    void insertClient(client toInsert) throws SQLException;
    List<client> findAllClient() throws SQLException;
    List<client> findClientById(int id) throws SQLException;
    void deleteClientById(int idBook) throws SQLException;
    void UpdateClientById(client toUpdate) throws SQLException;
}
