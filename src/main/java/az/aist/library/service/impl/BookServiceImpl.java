package az.aist.library.service.impl;

import az.aist.library.model.Book;
import az.aist.library.model.BookStatus;
import az.aist.library.repository.inter.BookRepository;
import az.aist.library.service.inter.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    public BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBookService() {
        List<Book> bookList = new ArrayList<>();
        try{
            bookList = bookRepository.getAllBook();
        }catch (Exception ex){
            log.error(""+ex);
        }
        return bookList;
    }

    @Override
    public boolean updateBookStatusService(Long bookId, int status) {
        boolean isExist = false;
        try{
            isExist = bookRepository.updateBookStatus(bookId,status);
        }catch (Exception ex){
            log.error(""+ex);
        }
        return isExist;
    }

    @Override
    public Book getBookInfoService(Long bookId) {
        Book book = new Book();
        try{
            book = bookRepository.getBookInfo(bookId);
        }catch (Exception ex){
            log.error(""+ex);
        }
        return book;
    }

    @Override
    public List<Book> getBookByNameService(String bookName) {
        List<Book> bookList = new ArrayList<>();
        try{
            bookList = bookRepository.getBookByName(bookName);
        }catch (Exception ex){
            log.error(""+ex);
        }
        return bookList;
    }

    @Override
    public boolean addNewBookService(Book book) {
        boolean isExist = false;
        try{
            isExist = bookRepository.addNewBook(book);
        }catch (Exception ex){
            log.error(""+ex);
        }
        return isExist;
    }

    @Override
    public List<BookStatus> getBookStatus() {
        List<BookStatus> status = new ArrayList<>();
        try{
            status = bookRepository.getStatusList();
        }catch (Exception ex){
            log.error(""+ex);
        }
        return status;
    }

    @Override
    public boolean updateBookService(Book book) {
        boolean isExist = false;
        try{
            isExist = bookRepository.updateBook(book);
        }catch (Exception ex){
            log.error(""+ex);
        }
        return isExist;
    }

    @Override
    public boolean deleteBookService(Long bookId) {
        boolean isExist = false;
        try{
            isExist = bookRepository.deleteBook(bookId);
        }catch (Exception ex){
            log.error(""+ex);
        }
        return isExist;
    }
}
