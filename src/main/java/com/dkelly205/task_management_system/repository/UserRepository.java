package com.dkelly205.task_management_system.repository;

import com.dkelly205.task_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);
}
