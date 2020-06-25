package com.fdmgroup.bank.repository;

import com.fdmgroup.bank.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;


public interface AccountDao extends JpaRepository<Account, Long> {

}
