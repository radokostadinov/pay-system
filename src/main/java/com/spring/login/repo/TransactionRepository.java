package com.spring.login.repo;

import com.spring.login.repo.model.Transaction;
import com.spring.login.util.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Modifying
    @Query("UPDATE Transaction t SET t.status = :status where t.id = :id")
    void setTransactionStatus(@Param("status") TransactionStatus status, @Param("id") int id);

    @Query("")
    void authorizeTransaction(@Param("amount") double amount);

    @Query("")
    void chargeTransaction(@Param("username") double amount);

    @Query("")
    void refundTransaction(@Param("username") String username);

}
