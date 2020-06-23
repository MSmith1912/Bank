package com.fdmgroup.bank.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_gen")
    @SequenceGenerator(name = "account_gen", sequenceName = "ACCOUNT_SEQ", allocationSize = 1)
    private long accountId;

    private long customerId;

    private BigDecimal balance;

    public Account() {
    }

    public Account(long customerId, BigDecimal balance) {
        this.customerId = customerId;
        this.balance = balance;
    }

    public BigDecimal credit(BigDecimal funds) {
        if(funds.signum() != -1) {
            balance = balance.add(funds);
        }
        return balance;
    }


}
