package com.fdmgroup.bank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.bank.models.Account;
import com.fdmgroup.bank.services.AccountService;
import com.fdmgroup.bank.services.CreditService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AccountTests {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    AccountService accountService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CreditService creditService;

    MockMvc mockMvc;

    MockHttpSession session;

    final static String ACCOUNT_ROOT_URI = "/accounts";

    @BeforeEach
    public void setUp() {
        this.session = new MockHttpSession();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SharedHttpSessionConfigurer.sharedHttpSession())
                .build();
    }

    @Test
    public void test_thatAnAccountCanBeCreated() {
        Account account = new Account(1, new BigDecimal("500.00"));
        List<Account> numberOfAccountsBefore = accountService.findAll();
        accountService.save(account);
        List<Account> numberOfAccountsAfter = accountService.findAll();

        assertNotEquals(numberOfAccountsBefore, numberOfAccountsAfter);
    }

    @Test
    public void test_ThatAnAccountCanBeCredited() throws Exception {
        Optional<Account> accountToCredit = accountService.findByAccountId(1L);
        accountToCredit.get().credit(new BigDecimal("100.00"));

        ResultActions result= this.mockMvc.perform(put(ACCOUNT_ROOT_URI + "/Credit")
                .session(session)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(accountToCredit)))
                .andExpect(status().isOk());
        String expectedResult = "{\"accountId\":1,\"customerId\":1,\"balance\":600.00}";
        Assertions.assertEquals(result.andReturn().getResponse().getContentAsString(), expectedResult);
    }

    @Test
    public void test_ThatAnAccountCanBeDebited() throws Exception {
        Optional<Account> accountToDebit = accountService.findByAccountId(1L);
        accountToDebit.get().debit(new BigDecimal("100.00"));

        ResultActions result= this.mockMvc.perform(put(ACCOUNT_ROOT_URI + "/Debit")
                .session(session)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(accountToDebit)))
                .andExpect(status().isOk());
        String expectedResult = "{\"accountId\":1,\"customerId\":1,\"balance\":400.00}";
        Assertions.assertEquals(expectedResult, result.andReturn().getResponse().getContentAsString());
    }


}
