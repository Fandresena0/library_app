package shadow.atomic.library.service;

import org.springframework.stereotype.Service;
import shadow.atomic.library.model.client;
import shadow.atomic.library.repository.DAO.ClientDAO;

import java.sql.SQLException;
import java.util.List;
@Service
public class ClientService {
    private ClientDAO clientDAO;

    public ClientService(ClientDAO clientDAO){
        this.clientDAO = clientDAO;
    }

    public List<client> findAllClient(){
        try {
            return clientDAO.findAllClient();
        } catch (SQLException e) {
            throw new RuntimeException("There has been an error when fetching all Clients");
        }
    }

    public List<client> findClientById(int id){
        try{
            return clientDAO.findClientById(id);
        }catch(SQLException e){
            throw new RuntimeException("There has been an error when fetching Client with id : "+id);
        }
    }

    public client insertClient(client toInsert){
        try{
            this.clientDAO.insertClient(toInsert);

            return toInsert;
        }catch(SQLException e){
            throw new RuntimeException("There was an error when inserting the Client.");
        }
    }

    public void deleteClient(int idClient){
        try {
            this.clientDAO.deleteClientById(idClient);
        }catch (SQLException e){
            throw new RuntimeException("error in delete action");
        }
    }

}
