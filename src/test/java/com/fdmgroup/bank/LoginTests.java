package com.fdmgroup.bank;

import com.fdmgroup.bank.models.AuthenticationRequest;
import com.fdmgroup.bank.models.Role;
import com.fdmgroup.bank.models.User;
import com.fdmgroup.bank.services.UserSecurityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.POST;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTests {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeEach
    public void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterEach
    public void close() {
        validatorFactory.close();
    }

    private AuthenticationRequest signupDto = new AuthenticationRequest("harry", "12345678", "Harry", "Wilson");
    private User user = new User(signupDto.getUsername(), signupDto.getPassword(), signupDto.getFirstname(), signupDto.getLastname(), new Role());

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private UserSecurityService userSecurityService;

    @Test
    public void test_ThatValidUserCanSignIn() {
        restTemplate.postForEntity("/login/LoginUser", new AuthenticationRequest("admin1", "12345678"), Void.class);
        verify(this.userSecurityService).signin("admin1", "12345678");
    }

    @Test
    public void test_ThatInvalidUserCannotSignIn() {
        restTemplate.postForEntity("/login/LoginUser", new AuthenticationRequest("admin1", "1234567"), Void.class);
        verifyNoInteractions(this.userSecurityService);
    }

    @Test
    public void registerInvalid() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        when(userSecurityService.signup(authenticationRequest.getUsername(), authenticationRequest.getPassword(), authenticationRequest.getFirstname(), authenticationRequest.getLastname())).thenReturn(Optional.of(user));

        ResponseEntity<User> responseEntity = restTemplate.exchange("/login/RegisterUser", POST,
                new HttpEntity<>(authenticationRequest),
                User.class);
        assertThat(responseEntity.getStatusCode().value(), is(400));
    }

    @Test
    public void registerUser(){
        when(userSecurityService.signup(signupDto.getUsername(), signupDto.getPassword(), signupDto.getFirstname(), signupDto.getLastname())).thenReturn(Optional.of(user));

        ResponseEntity<User> responseEntity = restTemplate.exchange("/login/RegisterUser", POST,
                new HttpEntity<>(signupDto),
                User.class);

        assertThat(responseEntity.getStatusCode().value(), is(201));
        assertThat(responseEntity.getBody().getUsername(), is(user.getUsername()));
        assertThat(responseEntity.getBody().getFirstName(), is(user.getFirstName()));
        assertThat(responseEntity.getBody().getLastName(), is(user.getLastName()));
        assertThat(responseEntity.getBody().getRoles().size(), is(user.getRoles().size()));
    }



}
