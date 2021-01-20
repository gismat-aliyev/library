package az.aist.library.repository.inter;

import az.aist.library.model.Transactions;

import java.util.List;

public interface TransactionRepository {
    List<Transactions> getPendingTransactionByUserId(Long userId);

    List<Transactions> getDeliveryTransactionByUserId(Long userId);

    boolean addTransaction(Long userId,Long bookId);

    boolean deleteTransaction(Long trId);

    boolean markTransactionDelivery(Long trId);

    List<Transactions> getDeliveryTransaction();

    List<Transactions> getAllPendingTransaction();

    Transactions getTransactionInfo(Long trId);



}
