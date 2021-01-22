package az.aist.library.repository.impl;

import az.aist.library.model.Transactions;
import az.aist.library.repository.inter.TransactionRepository;
import az.aist.library.repository.mapper.TransactionMapper;
import az.aist.library.repository.sql.LibrarySQL;
import az.aist.library.service.inter.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Slf4j
public class TransactionRepositoryImpl implements TransactionRepository {


    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TransactionMapper transactionMapper;

    public BookService bookService;

    @Autowired
    public TransactionRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                     TransactionMapper transactionMapper,
                                     BookService bookService) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.transactionMapper = transactionMapper;
        this.bookService = bookService;
    }

    @Override
    public List<Transactions> getPendingTransactionByUserId(Long userId) {
        try{
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("userId", userId);
            mapSqlParameterSource.addValue("status", 1);
            List<Transactions> transactions = namedParameterJdbcTemplate
                    .query(LibrarySQL.GET_TR_BY_USER_ID, mapSqlParameterSource, transactionMapper::getTransactionList);
            return transactions;
        }catch (Exception ex){
            log.error(""+ex);
            return null;
        }

    }

    @Override
    public List<Transactions> getDeliveryTransactionByUserId(Long userId) {
        try{
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("userId", userId);
            mapSqlParameterSource.addValue("status", 2);
            List<Transactions> transactions = namedParameterJdbcTemplate
                    .query(LibrarySQL.GET_TR_BY_USER_ID, mapSqlParameterSource, transactionMapper::getTransactionList);
            return transactions;
        }catch (Exception ex){
            log.error(""+ex);
            return null;
        }

    }

    @Override
    @Transactional
    public boolean addTransaction(Long userId, Long bookId) {
        try{
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("userId", userId);
            mapSqlParameterSource.addValue("bookId", bookId);
            int count = namedParameterJdbcTemplate.update(LibrarySQL.ADD_TR, mapSqlParameterSource);
            int result = 0;
            if(count > 0){
                MapSqlParameterSource mps = new MapSqlParameterSource();
                mps.addValue("bookId", bookId);
                mps.addValue("status", 2);
                result = namedParameterJdbcTemplate.update(LibrarySQL.BOOK_STATUS_UPDATE,mps);
            }

            return result > 0;
        }catch (Exception ex){
            log.error(""+ex);
            return false;
        }

    }

    @Override
    @Transactional
    public boolean deleteTransaction(Long trId) {
        try{
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("trId", trId);
            mapSqlParameterSource.addValue("status", 0);
            int count = namedParameterJdbcTemplate.update(LibrarySQL.MAIN_UPDATE_TR, mapSqlParameterSource);
            if (count > 0){
                updateBookStatus(trId);
            }
            return count > 0;
        }catch (Exception ex){
            log.error(""+ex);
            return false;
        }

    }

    @Transactional
    public void updateBookStatus(Long trId){
        try{
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("trId", trId);
            Transactions transaction = namedParameterJdbcTemplate
                    .query(LibrarySQL.GET_TR_FOR_DELETE, mapSqlParameterSource, transactionMapper::getTransaction);

            bookService.updateBookStatusService(transaction.getBooks().getBookId(),1);
        }catch (Exception ex){
            log.error(""+ex);
        }

    }

    @Override
    public boolean markTransactionDelivery(Long trId) {
        try{
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("trId", trId);
            mapSqlParameterSource.addValue("status", 2);
            int count = namedParameterJdbcTemplate.update(LibrarySQL.MAIN_UPDATE_TR, mapSqlParameterSource);
            if(count>0){
                MapSqlParameterSource mapSqlParameterSource1 = new MapSqlParameterSource();
                mapSqlParameterSource1.addValue("trId", trId);
                Transactions transaction = namedParameterJdbcTemplate
                        .query(LibrarySQL.GET_TR_INFO, mapSqlParameterSource, transactionMapper::getTransaction);
                bookService.updateBookStatusService(transaction.getBooks().getBookId(),3);
            }
            return count > 0;
        }catch (Exception ex){
            log.error(""+ex);
            return false;
        }

    }

    @Override
    public List<Transactions> getDeliveryTransaction() {
        try{
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("status", 2);
            List<Transactions> transactions = namedParameterJdbcTemplate
                    .query(LibrarySQL.GET_TR_LIST, mapSqlParameterSource, transactionMapper::getTransactionList);
            return transactions;
        }catch (Exception ex){
            log.error(""+ex);
            return null;
        }

    }

    @Override
    public List<Transactions> getAllPendingTransaction() {
        try{
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("status", 1);
            List<Transactions> transactions = namedParameterJdbcTemplate
                    .query(LibrarySQL.GET_TR_LIST, mapSqlParameterSource, transactionMapper::getTransactionList);
            return transactions;
        }catch (Exception ex){
            log.error(""+ex);
            return null;
        }

    }

    @Override
    public Transactions getTransactionInfo(Long trId) {
        try{
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("trId", trId);
            Transactions transaction = namedParameterJdbcTemplate
                    .query(LibrarySQL.GET_TR_INFO, mapSqlParameterSource, transactionMapper::getTransaction);
            return transaction;
        }catch (Exception ex){
            log.error(""+ex);
            return null;
        }

    }
}
