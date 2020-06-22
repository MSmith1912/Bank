package com.fdmgroup.bank;

import com.fdmgroup.bank.models.Account;
import com.fdmgroup.bank.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountTests {

    @Autowired
    AccountService accountService;


    MockMvc mockMvc;

    MockHttpSession session;

    final static String HOTEL_ROOT_URI = "/hotel";

    @Test
    public void test_thatAnAccountCanBECreated() {
        Account account = new Account();
        List<Account> numerOfAccountsBefore = accountService.findAll();
        accountService.save(account);
        List<Account> numerOfAccountsAfter = accountService.findAll();



    }
}
