package com.fdmgroup.bank.services;

import com.fdmgroup.bank.models.Account;
import com.fdmgroup.bank.repository.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountDao accountDao;

    public Account save(Account account) { return accountDao.save(account); }

    public List<Account> findAll() { return accountDao.findAll(); }

    public Optional<Account> findByAccountId(long accountId) { return accountDao.findById(accountId); }

    public BigDecimal getBalance(long accountId) {
        Optional<Account> account = accountDao.findById(accountId);
        BigDecimal balance = account.get().getBalance();
        return balance;
    }
}
