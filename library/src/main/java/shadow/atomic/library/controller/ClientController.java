package shadow.atomic.library.controller;

import org.springframework.web.bind.annotation.*;
import shadow.atomic.library.model.client;
import shadow.atomic.library.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    private shadow.atomic.library.service.ClientService ClientService;

    public ClientController(ClientService ClientService){
        this.ClientService = ClientService;
    }

    @GetMapping("/")
    public List<client> getAllClients(){
        return ClientService.findAllClient();
    }

    @GetMapping("/{id}")
    public List<client> getClientById(@PathVariable int id){
        return ClientService.findClientById(id);
    }

    @PostMapping("/insert")
    public String insertClient(@RequestBody client toInsert){
        return "the Client "+ClientService.insertClient(toInsert)+" is inserted";
    }

    @DeleteMapping ("/delete/{id}")
    public String deleteClient(@PathVariable int id){
        ClientService.deleteClient(id);
        return "the Client with id = "+id+" is deleted";
    }
}
