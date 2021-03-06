package com.fdmgroup.bank.services;

import com.fdmgroup.bank.models.User;
import com.fdmgroup.bank.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User save(User newUser) { return userDao.save(newUser); }

    public List<User> findAll() { return userDao.findAll(); }

    public Page<User> findAll(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        return userDao.findAll(pageRequest);
    }

    public Optional<User> findByUserId(long userId) { return userDao.findById(userId); }

    public Optional<User> findByUsername(String username) { return userDao.findByUsername(username); }
}
