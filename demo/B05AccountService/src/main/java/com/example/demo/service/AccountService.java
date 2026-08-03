package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Account;

public interface AccountService {

    Account addAccount(Account account);

    List<Account> getAllAccounts();

    Account getAccountById(Long id);

    Account getAccountByAccountNumber(String accountNumber);

    Account updateAccount(Long id, Account account);

    void deleteAccount(Long id);

    Account freezeAccount(Long id);

    Account unfreezeAccount(Long id);
}
