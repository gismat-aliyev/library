package az.aist.library.repository.mapper;

import az.aist.library.model.Book;
import az.aist.library.model.BookStatus;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookMapper {

    public List<Book> getBookList(ResultSet resultSet) throws SQLException {
        List<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            Book book = new Book();
            BookStatus status = new BookStatus();
            book.setBookId(resultSet.getLong("book_id"));
            book.setBookName(resultSet.getString("book_name"));
            book.setAbout(resultSet.getString("about"));
            book.setAuthor(resultSet.getString("author"));
            book.setAddedDate(resultSet.getDate("added_date"));
            book.setCreateDate(resultSet.getDate("create_date_of_book"));
            status.setStatusId(resultSet.getLong("status_id"));
            status.setStatusName(resultSet.getString("status_name"));
            book.setStatus(status);
            books.add(book);
        }
        return books;
    }

    public Book getBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        if (resultSet.next()) {
            BookStatus status = new BookStatus();
            book.setBookId(resultSet.getLong("book_id"));
            book.setBookName(resultSet.getString("book_name"));
            book.setAbout(resultSet.getString("about"));
            book.setAuthor(resultSet.getString("author"));
            book.setAddedDate(resultSet.getDate("added_date"));
            book.setCreateDate(resultSet.getDate("create_date_of_book"));
            status.setStatusId(resultSet.getLong("status_id"));
            status.setStatusName(resultSet.getString("status_name"));
            book.setStatus(status);
        }
        return book;
    }

    public List<BookStatus> getStatusList(ResultSet rs) throws SQLException {
        List<BookStatus> bookStatusList = new ArrayList<>();
        while (rs.next()) {
            BookStatus status = new BookStatus();
            status.setStatusName(rs.getString("status_name"));
            status.setStatusId(rs.getLong("status_id"));
            bookStatusList.add(status);
        }
        return bookStatusList;
    }
}
