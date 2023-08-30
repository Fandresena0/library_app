package shadow.atomic.library.controller;

import org.springframework.web.bind.annotation.*;
import shadow.atomic.library.model.borrowing;
import shadow.atomic.library.service.BorrowingService;

import java.util.List;

@RestController
@RequestMapping("/borrowing")
public class BorrowingController {
    private BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService){
        this.borrowingService = borrowingService;
    }

    @GetMapping("/")
    public List<borrowing> getAllBorrowing(){
        return borrowingService.findAllBorrowing();
    }

    @GetMapping("/{id}")
    public List<borrowing> getBorrowingById(@PathVariable int id){
        return borrowingService.findBorrowingById(id);
    }

    @PostMapping("/insert")
    public String insertBorrowing(@RequestBody borrowing toInsert){
        return "the Borrowing \n\n"+ borrowingService.insertBorrowing(toInsert)+" \n\n is inserted";
    }

    @DeleteMapping ("/delete/{id}")
    public String deleteBorrowing(@PathVariable int id){
        borrowingService.deleteBorrowing(id);
        return "the Borrowing with id = "+id+" is deleted";
    }
}
