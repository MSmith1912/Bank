package com.fdmgroup.bank.controller.utility;

import com.fdmgroup.bank.models.Account;
import com.fdmgroup.bank.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PutMapping("/Credit")
    public ResponseEntity<Account> creditAccount(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.save(account));
    }

}
