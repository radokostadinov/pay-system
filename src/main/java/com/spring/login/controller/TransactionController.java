package com.spring.login.controller;

import com.spring.login.repo.model.Client;
import com.spring.login.repo.model.ClientTransaction;
import com.spring.login.repo.model.Transaction;
import com.spring.login.service.ClientDetailService;
import com.spring.login.service.MerchantService;
import com.spring.login.service.TransactionService;
import com.spring.login.util.MerchantStatus;
import com.spring.login.util.TokenUtil;
import com.spring.login.util.TransactionStatus;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    private TokenUtil jwtTokenUtil;

    @Autowired
    private ClientDetailService jwtUserDetailsService;

    @Autowired
    private MerchantService merchantService;


    @PostMapping(value = "/chargetransaction", consumes = "application/json")
    private String chargeTransaction(@RequestBody ClientTransaction clientTransaction,
                                     HttpServletResponse response) {

        String requestTokenHeader = response.getHeader("Authorization");

        String clientName = null;
        if (requestTokenHeader != null) {
            try {
                clientName = jwtTokenUtil.getClienUsernameFromToken(requestTokenHeader);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            System.out.println("No JWT Token found!");
        }

        Client clientDetails = this.jwtUserDetailsService.loadClientByName(clientName);
        if (jwtTokenUtil.validateToken(requestTokenHeader, clientDetails)) {

            TransactionStatus transactionStatus;
            MerchantStatus merchantStatus = merchantService.getMerchantStatusByProductUuid(clientTransaction.getUuid());

            if (MerchantStatus.INACTIVE == merchantStatus) {
                transactionStatus = TransactionStatus.REVERSED;
            } else {
                transactionStatus = TransactionStatus.APPROVED;
            }

            Transaction transaction = createTransactionEntity(clientTransaction, transactionStatus);

            long transactionId = transactionService.saveOrUpdate(transaction).getId();

            return String.format("Your transaction was successfully submitted with id %d", transactionId);
        } else {
            return String.format("Your token is not valid!");
        }
    }

    private Transaction createTransactionEntity(
            ClientTransaction clientTransaction,
            TransactionStatus transactionStatus) {

        Transaction transaction = new Transaction();
        transaction.setUuid(clientTransaction.getUuid());
        transaction.setAmount(clientTransaction.getAmount());
        transaction.setStatus(transactionStatus);
        transaction.setCustomerEmail(clientTransaction.getClientEmail());
        transaction.setCustomerPhone(clientTransaction.getClientPhone());

        return transaction;
    }

    @PostMapping("/refundtransaction")
    private long refundTransaction(@RequestBody Transaction transaction) {
        transactionService.saveOrUpdate(transaction);
        return transaction.getId();
    }

    private long authorizeTransaction(Transaction transaction) {

        transactionService.saveOrUpdate(transaction);
        return transaction.getId();
    }


    @GetMapping("/transaction")
    private List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/transaction/{id}")
    private Transaction getTransaction(@PathVariable("id") int id) {
        return transactionService.getTransactionById(id);
    }

    @DeleteMapping("/transaction/{id}")
    private void deleteTransaction(@PathVariable("id") int id) {
        transactionService.delete(id);
    }

    @PostMapping("/transaction")
    private long saveTransaction(@RequestBody Transaction transaction) {
        transactionService.saveOrUpdate(transaction);
        return transaction.getId();
    }
}
