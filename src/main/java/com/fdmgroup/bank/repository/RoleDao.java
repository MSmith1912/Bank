package com.fdmgroup.bank.repository;

import com.fdmgroup.bank.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDao extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String role_customer);
}
