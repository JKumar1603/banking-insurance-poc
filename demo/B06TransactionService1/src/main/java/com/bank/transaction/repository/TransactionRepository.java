package com.bank.transaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.transaction.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountNumber(String accountNumber);
}