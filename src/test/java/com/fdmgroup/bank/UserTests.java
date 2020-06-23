package com.fdmgroup.bank;

import com.fdmgroup.bank.models.User;
import com.fdmgroup.bank.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

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
        newUser.setFirstName("Harry");
        newUser.setLastName("Styles");

        userService.save(newUser);

        List<User> allUsers = userService.findAll();

        assert(allUsers.size() > 0);
    }

    @Test
    public void test_ThatAllUsersCanBeRetrieved(){
        List<User> allUsers = userService.findAll();
        assertTrue(allUsers.size() > 0);
    }

    @Test
    public void testThatAUserCanBeRetrievedById() {
        addUserToDataBase();
        Optional<User> userFromDB = userService.findByUserId(1L);
        Optional<User> userFromMethod = userService.findByUserId(userFromDB.get().getUserId());

        assertEquals(userFromDB.get().getUserId(), userFromMethod.get().getUserId());
    }

    @Test
    public void testThatAUserCanBeRetrievedByUsername() {
        addUserToDataBase();
        Optional<User> userFromDB = userService.findByUsername("admin1");
        Optional<User> userFromMethod = userService.findByUsername(userFromDB.get().getUsername());

        assertEquals(userFromDB.get().getUserId(), userFromMethod.get().getUserId());
    }

    private void addUserToDataBase() {
        User newUser = new User();
        newUser.setUsername("admin1");
        newUser.setPassword("password");
        newUser.setFirstName("Admin");
        newUser.setLastName("Administrator");
        userService.save(newUser);
    }

}
