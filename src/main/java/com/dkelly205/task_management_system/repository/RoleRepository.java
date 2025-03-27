package com.dkelly205.task_management_system.repository;

import com.dkelly205.task_management_system.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
