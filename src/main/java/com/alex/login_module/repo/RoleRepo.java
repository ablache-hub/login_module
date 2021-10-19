package com.alex.login_module.repo;

import com.alex.login_module.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);

}
