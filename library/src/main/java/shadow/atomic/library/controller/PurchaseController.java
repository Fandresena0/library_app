package shadow.atomic.library.controller;

import org.springframework.web.bind.annotation.*;
import shadow.atomic.library.model.purchase;
import shadow.atomic.library.service.PurchaseService;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    private PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService){
        this.purchaseService = purchaseService;
    }

    @GetMapping("/")
    public List<purchase> getAllPurchases(){
        return purchaseService.findAllPurchase();
    }

    @GetMapping("/{id}")
    public List<purchase> getPurchaseById(@PathVariable int id){
        return purchaseService.findPurchaseById(id);
    }

    @PostMapping("/insert")
    public String insertPurchase(@RequestBody purchase toInsert){
        return "the Purchase \n\n"+ purchaseService.insertPurchase(toInsert)+" \n\n is inserted";
    }

    @DeleteMapping ("/delete/{id}")
    public String deletePurchase(@PathVariable int id){
        purchaseService.deletePurchase(id);
        return "the Purchase with id = "+id+" is deleted";
    }
}
