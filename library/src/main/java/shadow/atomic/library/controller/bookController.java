package shadow.atomic.library.controller;

import org.springframework.web.bind.annotation.*;
import shadow.atomic.library.model.book;
import shadow.atomic.library.service.bookService;

import java.util.List;

@RestController
@RequestMapping("/book")
public class bookController {
    private bookService bookService;

    public bookController(bookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/")
    public List<book> getAllbooks(){
        return bookService.findAllbooks();
    }

    @GetMapping("/{id}")
    public List<book> getbookById(@PathVariable int id){
        return bookService.findbookById(id);
    }

    @PostMapping("/insert")
    public String insertbook(@RequestBody book toInsert){
        return "the "+bookService.insertBook(toInsert)+" is inserted";
    }

    @DeleteMapping ("/delete/{id}")
    public String deleteBook(@PathVariable int id){
        bookService.deleteBook(id);
        return "the book with id = "+id+" is deleted";
    }

    @PutMapping("/update")
    public book updateBook(@RequestBody book toUpdate){
        return bookService.updateBook(toUpdate);
    }
}

