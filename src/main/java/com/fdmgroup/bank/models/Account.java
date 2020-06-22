package com.fdmgroup.bank.models;

import javax.persistence.*;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_gen")
    @SequenceGenerator(name = "account_gen", sequenceName = "ACCOUNT_SEQ", allocationSize = 1)
    private long accountId;

    public Account() {
    }
}
