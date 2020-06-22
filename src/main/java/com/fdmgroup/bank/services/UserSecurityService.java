package com.fdmgroup.bank.services;

import com.fdmgroup.bank.models.Role;
import com.fdmgroup.bank.models.User;
import com.fdmgroup.bank.repository.UserDao;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class UserSecurityService {

    private UserDao userDao;

    private AuthenticationManager authenticationManager;

    private RoleDao roleDao;

    private PasswordEncoder passwordEncoder;

    public Optional<User> signup(String username, String password, String firstName, String lastName){
        Optional<User> user = Optional.empty();
        if (!userDao.findByUsername(username).isPresent()) {
            Optional<Role> role = roleDao.findByRoleName("ROLE_CUSTOMER");
            user = Optional.of(userDao.save(new User(username,
                    passwordEncoder.encode(password),
                    firstName,
                    lastName,
                    role.get())));
        }
        return user;
    }

}
