package az.aist.library;

import az.aist.library.repository.inter.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionsRepositoryTest {

    @Autowired
    public TransactionRepository transactionRepository;

    @Test
    void getPendingByUserId(){
        System.out.println(transactionRepository.getPendingTransactionByUserId(1L));
    }

    @Test
    void getDeliveryByUSerId(){
        System.out.println(transactionRepository.getDeliveryTransactionByUserId(1L));
    }

    @Test
    void addTr(){
        System.out.println(transactionRepository.addTransaction(2L,2L));
    }

    @Test
    void accessBook(){
        System.out.println(transactionRepository.markTransactionDelivery(3L));
    }

    @Test
    void getDelivery(){
        System.out.println(transactionRepository.getDeliveryTransactionByUserId(2L));
    }

    @Test
    void deleteTr(){
        System.out.println(transactionRepository.deleteTransaction(1L));
    }

    @Test
    void getAllDeliveryTr(){
        System.out.println(transactionRepository.getDeliveryTransaction());
    }

    @Test
    void getAllPendingTr(){
        System.out.println(transactionRepository.getAllPendingTransaction());
    }

    @Test
    void getTrInfoById(){
        System.out.println(transactionRepository.getTransactionInfo(3L));
    }
}
