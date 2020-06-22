package com.fdmgroup.bank.services;

import com.fdmgroup.bank.models.Role;
import com.fdmgroup.bank.models.User;
import com.fdmgroup.bank.repository.RoleDao;
import com.fdmgroup.bank.repository.UserDao;
import com.fdmgroup.bank.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSecurityService {

    private UserDao userDao;

    private AuthenticationManager authenticationManager;

    private RoleDao roleDao;

    private PasswordEncoder passwordEncoder;

    private JwtProvider jwtProvider;

    @Autowired
    public UserSecurityService(UserDao userDao, AuthenticationManager authenticationManager,
                               PasswordEncoder passwordEncoder, RoleDao roleDao, JwtProvider jwtProvider) {
        this.userDao = userDao;
        this.authenticationManager = authenticationManager;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

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
