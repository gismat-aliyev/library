package az.aist.library.controller;

import az.aist.library.model.Transactions;
import az.aist.library.security.CustomUserDetails;
import az.aist.library.service.inter.AuthenticationFacade;
import az.aist.library.service.inter.TransactionService;
import az.aist.library.service.inter.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tr")
public class TransactionController {

    public TransactionService transactionService;

    public AuthenticationFacade authenticationFacade;

    public UserService userService;

    @Autowired
    public TransactionController(TransactionService transactionService,
                                 AuthenticationFacade authenticationFacade,
                                 UserService userService) {
        this.transactionService = transactionService;
        this.authenticationFacade = authenticationFacade;
        this.userService = userService;
    }

    @GetMapping("/getPendingTransactionByUserId")
    public ResponseEntity<?> getPendingTransactionByUserId() {
        CustomUserDetails customUserDetails = (CustomUserDetails) authenticationFacade.getAuthentication().getPrincipal();
        val rs = userService.getUserByLoginService(customUserDetails.getUsername());
        List<Transactions> transactions = transactionService.getPendingTransactionByUserIdService(rs.getUserId());
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/getDeliveryTransactionByUserId")
    public ResponseEntity<?> getDeliveryTransactionByUserId() {
        CustomUserDetails customUserDetails = (CustomUserDetails) authenticationFacade.getAuthentication().getPrincipal();
        val rs = userService.getUserByLoginService(customUserDetails.getUsername());
        List<Transactions> transactions = transactionService.getDeliveryTransactionByUserIdService(rs.getUserId());
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @RequestMapping(value = "/addTransaction", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<?> addTransaction(@RequestParam("bookId") Long bookId) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authenticationFacade.getAuthentication().getPrincipal();
        val rs = userService.getUserByLoginService(customUserDetails.getUsername());
        boolean result = transactionService.addTransactionService(rs.getUserId(), bookId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/deleteTransaction")
    public ResponseEntity<?> deleteTransaction(@RequestParam("trId") Long trId) {
        boolean result = transactionService.deleteTransactionService(trId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/markTransactionDelivery")
    public ResponseEntity<?> markTransactionDelivery(@RequestParam("trId") Long trId) {
        boolean result = transactionService.markTransactionDeliveryService(trId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getDeliveryTransactions")
    public ResponseEntity<?> getDeliveryTransactions() {
        List<Transactions> transactions = transactionService.getDeliveryTransactionService();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/getPendingTransactions")
    public ResponseEntity<?> getPendingTransactions() {
        List<Transactions> transactions = transactionService.getAllPendingTransactionService();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/getTransactionInfo")
    public ResponseEntity<?> getTransactionInfo(@RequestParam("trId") Long trId) {
        Transactions transaction = transactionService.getTransactionInfoService(trId);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}
