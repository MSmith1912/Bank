package com.fdmgroup.bank.repository;

import com.fdmgroup.bank.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {


}
