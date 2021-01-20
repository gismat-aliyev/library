package az.aist.library.repository.inter;

import az.aist.library.model.Book;
import az.aist.library.model.BookStatus;

import java.util.List;

public interface BookRepository {

    List<Book> getAllBook();

    boolean updateBookStatus(Long bookId,int status);

    Book getBookInfo(Long bookId);

    List<Book> getBookByName(String bookName);

    boolean addNewBook(Book book);

    List<BookStatus> getStatusList();

    boolean updateBook(Book book);

    boolean deleteBook(Long bookId);
}
