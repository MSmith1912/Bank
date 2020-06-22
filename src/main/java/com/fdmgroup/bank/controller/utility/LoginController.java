package com.fdmgroup.bank.controller.utility;

import com.fdmgroup.bank.models.AuthenticationRequest;
import com.fdmgroup.bank.models.User;
import com.fdmgroup.bank.services.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class LoginController {

    @Autowired
    private UserSecurityService userSecurityService;

    @PostMapping("/RegisterUser")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody @Valid AuthenticationRequest authRequest) {
        return userSecurityService.signup(authRequest.getUsername(), authRequest.getPassword(), authRequest.getFirstname(), authRequest.getLastname()).orElseThrow(() ->
                new HttpServerErrorException(HttpStatus.BAD_REQUEST, "User already exists"));
    }
}
