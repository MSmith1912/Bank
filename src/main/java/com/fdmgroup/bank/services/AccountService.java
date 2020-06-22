package com.fdmgroup.bank.services;

import com.fdmgroup.bank.models.Account;
import com.fdmgroup.bank.repository.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AccountService {

    @Autowired
    AccountDao accountDao;

    public void save(Account account) {
    }

    public List<Account> findAll() { return accountDao.findAll(); }
}
