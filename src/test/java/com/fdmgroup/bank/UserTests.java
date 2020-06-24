package com.fdmgroup.bank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.bank.models.User;
import com.fdmgroup.bank.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTests {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    MockHttpSession session;

    final static String USER_ROOT_URI = "/user";

    @BeforeEach
    public void setUp() {
        this.session = new MockHttpSession();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SharedHttpSessionConfigurer.sharedHttpSession())
                .build();
    }

    @AfterEach
    public void cleanUp() {
        this.session = null;
        this.mockMvc = null;
    }

    @Autowired
    UserService userService;

    //database set up test
    @Test
    public void test_ThatAUserCanBeCreated(){
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
    public void test_ThatValidUserCanBeAdded() throws Exception {
        User user = new User();
        this.mockMvc.perform(post(USER_ROOT_URI + "/AddUser")
            .session(session)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    //database set up test
    @Test
    public void test_ThatAllUsersCanBeRetrieved(){
        List<User> allUsers = userService.findAll();
        assertTrue(allUsers.size() > 0);
    }

    //database set up test
    @Test
    public void test_ThatAUserCanBeRetrievedById() {
        addUserToDataBase();
        Optional<User> userFromDB = userService.findByUserId(1L);
        Optional<User> userFromMethod = userService.findByUserId(userFromDB.get().getUserId());

        assertEquals(userFromDB.get().getUserId(), userFromMethod.get().getUserId());
    }

    //database set up test
    @Test
    public void test_ThatAUserCanBeRetrievedByUsername() {
        addUserToDataBase();
        Optional<User> userFromDB = userService.findByUsername("admin1");
        Optional<User> userFromMethod = userService.findByUsername(userFromDB.get().getUsername());

        assertEquals(userFromDB.get().getUserId(), userFromMethod.get().getUserId());
    }

    @Test
    public void test_ThatAUserExists_andCanBeReturned() throws Exception {
        addUserToDataBase();
        ResultActions mvcResult = this.mockMvc.perform(get(USER_ROOT_URI + "/SeeUser/user1")
            .session(session))
            .andExpect(status().isFound());

        String expectedResult = "{\"userId\":5,\"username\":\"user1\",\"password\":\"password\",\"firstName\":\"Admin\",\"lastName\":\"Administrator\",\"roles\":[]}";

        assertEquals(mvcResult.andReturn().getResponse().getContentAsString(), expectedResult);
    }

    @Test
    public void test_ThatAListOfUsersIsReturned() {
        List<User> allUsers = userService.findAll();
        assert(allUsers.size() > 0);
    }

    @Test
    public void test_ThatAPageOfUsersIsReturned() throws Exception {
        List<User> allUsers = userService.findAll();

        MvcResult result = this.mockMvc.perform(get(USER_ROOT_URI + "/AllUsers")
            .param("page", "0").param("size", "2"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    private void addUserToDataBase() {
        User newUser = new User();
        newUser.setUsername("user1");
        newUser.setPassword("password");
        newUser.setFirstName("Admin");
        newUser.setLastName("Administrator");
        userService.save(newUser);
    }

}
