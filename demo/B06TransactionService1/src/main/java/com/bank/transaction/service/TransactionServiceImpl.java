package com.bank.transaction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.transaction.entity.Transaction;
import com.bank.transaction.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return repository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    @Override
    public List<Transaction> getTransactionHistory(String accountNumber) {
        return repository.findByAccountNumber(accountNumber);
    }

    @Override
    public void deleteTransaction(Long id) {
        repository.deleteById(id);
    }
    @Override
    public Transaction getTransactionById(Long id) {

        return repository.findById(id).orElse(null);
    }
}