package com.fdmgroup.bank;

import com.fdmgroup.bank.models.User;
import com.fdmgroup.bank.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

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

    @Test
    public void testThatAUserCanBeRetrievedById() {
        addUserToDataBase();
        Optional<User> userFromDB = userService.findByUserId(1L);
        Optional<User> userFromMethod = userService.findByUserId(userFromDB.get().getUserId());

        assertEquals(userFromDB.get().getUserId(), userFromMethod.get().getUserId());

    }

    private void addUserToDataBase() {
        User newUser = new User();
        newUser.setUsername("admin1");
        newUser.setPassword("password");
        newUser.setFirstname("Admin");
        newUser.setLastname("Administrator");

        userService.save(newUser);
    }

}
