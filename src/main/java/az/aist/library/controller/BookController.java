package az.aist.library.controller;

import az.aist.library.model.Book;
import az.aist.library.model.BookStatus;
import az.aist.library.service.inter.BookService;
import az.aist.library.util.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    public BookService bookService;

    public DateConverter dateConverter;

    @Autowired
    public BookController(BookService bookService,DateConverter dateConverter) {
        this.bookService = bookService;
        this.dateConverter = dateConverter;
    }

    @GetMapping("/getAllBook")
    public ResponseEntity<?> getAllBook() {
        List<Book> bookList = bookService.getAllBookService();
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @GetMapping("/getStatus")
    public ResponseEntity<?> getStatus(){
        List<BookStatus> bookStatus = bookService.getBookStatus();
        return new ResponseEntity<>(bookStatus, HttpStatus.OK);
    }

    @RequestMapping(value = "/updateBook", method = {RequestMethod.PUT, RequestMethod.GET})
    public ResponseEntity<?> updateBookStatus(@RequestParam("bookId") Long bookId,
                                              @RequestParam("bookName") String bookName,
                                              @RequestParam("author") String author,
                                              @RequestParam(value = "about",required = false) String about,
                                              @RequestParam("createDate")String createDate,
                                              @RequestParam("status") Long status) {

        boolean result = bookService.updateBookService(new Book(
                bookId,
                bookName,
                author,
                about,
                null,
                dateConverter.stringToDate(createDate),
                new BookStatus(status,null)
        ));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getBookInfo")
    public ResponseEntity<?> getBookInfo(@RequestParam("bookId") Long bookId) {
        Book book = bookService.getBookInfoService(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/getBookByName")
    public ResponseEntity<?> getBookByName(@RequestParam("bookName") String bookName) {
        List<Book> bookList = bookService.getBookByNameService(bookName);
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @RequestMapping(value = "/addBook", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<?> addBook(@RequestParam("bookName") String bookName,
                                     @RequestParam("author") String author,
                                     @RequestParam(value = "about",required = false) String about,
                                     @RequestParam("createDate")String createDate,
                                     @RequestParam("status") Long status) {

        boolean result = bookService.addNewBookService(new Book(
                null,
                bookName,
                author,
                about,
                null,
                dateConverter.stringToDate(createDate),
                new BookStatus(status,null)
        ));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/deleteBook")
    public ResponseEntity<?> deleteBook(@RequestParam("bookId") Long bookId){
        boolean result = bookService.deleteBookService(bookId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
