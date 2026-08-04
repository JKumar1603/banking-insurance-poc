package com.bank.transaction.service;

import java.util.List;

import com.bank.transaction.entity.Transaction;

public interface TransactionService {

    Transaction saveTransaction(Transaction transaction);

    List<Transaction> getAllTransactions();

    List<Transaction> getTransactionHistory(String accountNumber);

    void deleteTransaction(Long id);

    Transaction getTransactionById(Long id);
}