package com.bank.transaction.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bank.transaction.entity.Transaction;
import com.bank.transaction.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping("/deposit")
    public Transaction deposit(@RequestBody Transaction transaction) {

        transaction.setTransactionType("DEPOSIT");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus("SUCCESS");

        return service.saveTransaction(transaction);
    }

    @PostMapping("/withdraw")
    public Transaction withdraw(@RequestBody Transaction transaction) {

        transaction.setTransactionType("WITHDRAW");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus("SUCCESS");

        return service.saveTransaction(transaction);
    }

    @PostMapping("/transfer")
    public Transaction transfer(@RequestBody Transaction transaction) {

        transaction.setTransactionType("TRANSFER");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus("SUCCESS");

        return service.saveTransaction(transaction);
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return service.getAllTransactions();
    }

    @GetMapping("/history/{accountNumber}")
    public List<Transaction> getHistory(
            @PathVariable String accountNumber) {

        return service.getTransactionHistory(accountNumber);
    }

    @PutMapping("/update/{id}")
    public Transaction updateTransaction(
            @PathVariable Long id,
            @RequestBody Transaction transaction) {

        transaction.setTransactionId(id);

        return service.saveTransaction(transaction);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTransaction(
            @PathVariable Long id) {

        service.deleteTransaction(id);

        return "Transaction Deleted Successfully";
    }
    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {

        return service.getTransactionById(id);
    }
    
}