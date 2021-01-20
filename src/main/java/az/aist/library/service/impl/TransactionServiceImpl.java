package az.aist.library.service.impl;

import az.aist.library.model.Transactions;
import az.aist.library.repository.inter.TransactionRepository;
import az.aist.library.service.inter.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    public TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transactions> getPendingTransactionByUserIdService(Long userId) {
        List<Transactions> transactions = new ArrayList<>();
        try {
            transactions = transactionRepository.getPendingTransactionByUserId(userId);
        } catch (Exception ex) {
            log.error("" + ex);
        }
        return transactions;
    }

    @Override
    public List<Transactions> getDeliveryTransactionByUserIdService(Long userId) {
        List<Transactions> transactions = new ArrayList<>();
        try {
            transactions = transactionRepository.getDeliveryTransactionByUserId(userId);
        } catch (Exception ex) {
            log.error("" + ex);
        }
        return transactions;
    }

    @Override
    public boolean addTransactionService(Long userId, Long bookId) {
        boolean isExist = false;
        try {
            isExist = transactionRepository.addTransaction(userId, bookId);
        } catch (Exception ex) {
            log.error("" + ex);
        }
        return isExist;
    }

    @Override
    public boolean deleteTransactionService(Long trId) {
        boolean isExist = false;
        try {
            isExist = transactionRepository.deleteTransaction(trId);
        } catch (Exception ex) {
            log.error("" + ex);
        }
        return isExist;
    }

    @Override
    public boolean markTransactionDeliveryService(Long trId) {
        boolean isExist = false;
        try {
            isExist = transactionRepository.markTransactionDelivery(trId);
        } catch (Exception ex) {
            log.error("" + ex);
        }
        return isExist;
    }

    @Override
    public List<Transactions> getDeliveryTransactionService() {
        List<Transactions> transactions = new ArrayList<>();
        try {
            transactions = transactionRepository.getDeliveryTransaction();
        } catch (Exception ex) {
            log.error("" + ex);
        }
        return transactions;
    }

    @Override
    public List<Transactions> getAllPendingTransactionService() {
        List<Transactions> transactions = new ArrayList<>();
        try {
            transactions = transactionRepository.getAllPendingTransaction();
        } catch (Exception ex) {
            log.error("" + ex);
        }
        return transactions;
    }

    @Override
    public Transactions getTransactionInfoService(Long trId) {
        Transactions transactions = new Transactions();
        try {
            transactions = transactionRepository.getTransactionInfo(trId);
        } catch (Exception ex) {
            log.error("" + ex);
        }
        return transactions;
    }
}
