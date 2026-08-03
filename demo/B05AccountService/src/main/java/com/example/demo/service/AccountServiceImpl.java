package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Account;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account Not Found With Id : " + id));
    }

    @Override
    public Account getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account Not Found"));
    }

    @Override
    public Account updateAccount(Long id, Account account) {
        Account existingAccount = getAccountById(id);

        existingAccount.setAccountType(account.getAccountType());
        existingAccount.setBalance(account.getBalance());
        existingAccount.setBranchName(account.getBranchName());
        existingAccount.setIfscCode(account.getIfscCode());

        return accountRepository.save(existingAccount);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account freezeAccount(Long id) {
        Account account = getAccountById(id);
        account.setStatus("FROZEN");
        return accountRepository.save(account);
    }

    @Override
    public Account unfreezeAccount(Long id) {
        Account account = getAccountById(id);
        account.setStatus("ACTIVE");
        return accountRepository.save(account);
    }
}
