package com.fdmgroup.bank;

import com.fdmgroup.bank.models.User;
import com.fdmgroup.bank.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTests {

    @Autowired
    UserService userService;

    @Test
    public void testThatAUserCanBeCreated(){
        User newUser = new User();
        newUser.setUsername("admin1");
        newUser.setPassword("password");
        newUser.setFirstname("Harry");
        newUser.setLastname("Styles");

        userService.save(newUser);

        List<User> allUsers = userService.findAll();

        assert(allUsers.size() > 0);

    }

}
