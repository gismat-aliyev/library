package az.aist.library.service.inter;

import az.aist.library.model.Book;
import az.aist.library.model.BookStatus;

import java.util.List;

public interface BookService {

    List<Book> getAllBookService();

    boolean updateBookStatusService(Long bookId,int status);

    Book getBookInfoService(Long bookId);

    List<Book> getBookByNameService(String bookName);

    boolean addNewBookService(Book book);

    List<BookStatus> getBookStatus();

    boolean updateBookService(Book book);

    boolean deleteBookService(Long bookId);
}
