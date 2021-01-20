package az.aist.library.service.inter;

import az.aist.library.model.Transactions;

import java.util.List;

public interface TransactionService {

    List<Transactions> getPendingTransactionByUserIdService(Long userId);

    List<Transactions> getDeliveryTransactionByUserIdService(Long userId);

    boolean addTransactionService(Long userId,Long bookId);

    boolean deleteTransactionService(Long trId);

    boolean markTransactionDeliveryService(Long trId);

    List<Transactions> getDeliveryTransactionService();

    List<Transactions> getAllPendingTransactionService();

    Transactions getTransactionInfoService(Long trId);

}
