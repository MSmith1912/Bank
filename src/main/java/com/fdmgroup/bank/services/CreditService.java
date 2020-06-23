package com.fdmgroup.bank.services;

import com.fdmgroup.bank.models.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CreditService {
    public void credit(Optional<Account> accountToCredit, BigDecimal bigDecimal) {
    }
}
