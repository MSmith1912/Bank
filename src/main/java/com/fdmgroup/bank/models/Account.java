package com.fdmgroup.bank.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

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


    public BigDecimal debit(BigDecimal funds) {
        if(funds.signum() != -1) {
            balance = balance.subtract(funds);
        }
        return balance;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId &&
                customerId == account.customerId &&
                balance.equals(account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, customerId, balance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", customerId=" + customerId +
                ", balance=" + balance +
                '}';
    }
}
