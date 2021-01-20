package az.aist.library.repository.impl;

import az.aist.library.model.Book;
import az.aist.library.model.BookStatus;
import az.aist.library.repository.inter.BookRepository;
import az.aist.library.repository.mapper.BookMapper;
import az.aist.library.repository.sql.LibrarySQL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class BookRepositoryImpl implements BookRepository {

    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public final BookMapper bookMapper;

    @Autowired
    public BookRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, BookMapper bookMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.bookMapper = bookMapper;
    }


    @Override
    public List<Book> getAllBook() {
        try{
            List<Book> bookList;
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            bookList = namedParameterJdbcTemplate.query(LibrarySQL.GET_BOOK_LIST, mapSqlParameterSource, bookMapper::getBookList);
            return bookList;
        }catch(Exception ex){
            log.error(""+ex);
            return null;
        }
    }

    @Override
    public boolean updateBookStatus(Long bookId, int status) {
        try{
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("bookId", bookId);
            mapSqlParameterSource.addValue("status", status);
            int count = namedParameterJdbcTemplate.update(LibrarySQL.BOOK_STATUS_UPDATE, mapSqlParameterSource);
            return count > 0;
        }catch (Exception ex){
            log.error(""+ex);
            return false;
        }

    }

    @Override
    public Book getBookInfo(Long bookId) {
        try{
            Book book;
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("id", bookId);
            book = namedParameterJdbcTemplate.query(LibrarySQL.GET_BOOK_BY_ID, mapSqlParameterSource, bookMapper::getBook);
            return book;
        }catch (Exception ex){
            log.error(""+ex);
            return null;
        }

    }

    @Override
    public List<Book> getBookByName(String bookName) {
        try{
            List<Book> book;
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            String name = "%" + bookName.toLowerCase() + "%";
            mapSqlParameterSource.addValue("name", name);
            book = namedParameterJdbcTemplate.query(LibrarySQL.GET_BOOK_BY_NAME, mapSqlParameterSource, bookMapper::getBookList);
            return book;
        }catch (Exception ex){
            log.error(""+ex);
            return null;
        }

    }

    @Override
    public boolean addNewBook(Book book) {
        try{
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("name", book.getBookName());
            mapSqlParameterSource.addValue("author", book.getAuthor());
            mapSqlParameterSource.addValue("about", book.getAbout());
            mapSqlParameterSource.addValue("add", book.getAddedDate());
            mapSqlParameterSource.addValue("create", book.getCreateDate());
            mapSqlParameterSource.addValue("status", book.getStatus().getStatusId());
            int count = namedParameterJdbcTemplate.update(LibrarySQL.ADD_BOOK, mapSqlParameterSource);
            return count > 0;
        }catch (Exception ex){
            log.error(""+ex);
            return false;
        }

    }

    @Override
    public List<BookStatus> getStatusList() {
        try{
            List<BookStatus> bookStatusList;
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            bookStatusList = namedParameterJdbcTemplate.query(LibrarySQL.GET_STATUS_LIST, mapSqlParameterSource, bookMapper::getStatusList);
            return bookStatusList;
        }catch (Exception ex){
            log.error(""+ex);
            return null;
        }

    }

    @Override
    public boolean updateBook(Book book) {
        try{
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("id", book.getBookId());
            mapSqlParameterSource.addValue("bookName", book.getBookName());
            mapSqlParameterSource.addValue("author", book.getAuthor());
            mapSqlParameterSource.addValue("about", book.getAbout());
            mapSqlParameterSource.addValue("createDate", book.getCreateDate());
            mapSqlParameterSource.addValue("status", book.getStatus().getStatusId());
            int count = namedParameterJdbcTemplate.update(LibrarySQL.UPDATE_BOOK_STATUS, mapSqlParameterSource);
            return count > 0;
        }catch (Exception ex){
            log.error(""+ex);
            return false;
        }

    }

    @Override
    public boolean deleteBook(Long bookId) {
        try{
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("id", bookId);
            int count = namedParameterJdbcTemplate.update(LibrarySQL.DELETE_BOOK, mapSqlParameterSource);
            return count > 0;
        }catch (Exception ex){
            log.error(""+ex);
            return false;
        }

    }
}
