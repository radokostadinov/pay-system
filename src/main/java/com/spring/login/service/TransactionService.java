package com.spring.login.service;

import com.spring.login.repo.TransactionRepository;
import com.spring.login.repo.model.Transaction;
import com.spring.login.util.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public void setTransactionStatus(TransactionStatus status, int id) {
        transactionRepository.setTransactionStatus(status, id);
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactionRepository.findAll().forEach(transaction -> transactions.add(transaction));
        return transactions;
    }

    public Transaction getTransactionById(int id) {
        return transactionRepository.findById(id).get();
    }

    public Transaction saveOrUpdate(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void delete(int id) {
        transactionRepository.deleteById(id);
    }
}
