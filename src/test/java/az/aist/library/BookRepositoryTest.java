package az.aist.library;

import az.aist.library.model.Book;
import az.aist.library.model.BookStatus;
import az.aist.library.repository.inter.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    public BookRepository bookRepository;

    @Test
    void contextLoads() {
        List<Book> bookList = bookRepository.getAllBook();
        System.out.println(bookList);
    }

    @Test
    void getBookById(){
        Book book = bookRepository.getBookInfo(1L);
        System.out.println(book);
    }

    @Test
    void getBookByName(){
        List<Book> book = bookRepository.getBookByName("a");
        System.out.println(book);
    }

    @Test
    void addBook(){
        BookStatus status = new BookStatus(1L,"");
        Book book = new Book(null,"Graph","Author2","Test",new Date(),new Date(),status);
        System.out.println(bookRepository.addNewBook(book));
    }

    @Test
    void updateBookStatus(){
        System.out.println(bookRepository.updateBookStatus(2L,1));
    }

}
