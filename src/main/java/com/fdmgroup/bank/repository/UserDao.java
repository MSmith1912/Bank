package com.fdmgroup.bank.repository;

import com.fdmgroup.bank.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findByUsername(@Param("username") String username);

}
