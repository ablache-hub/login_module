package com.alex.login_module.repo;

import com.alex.login_module.auth.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);

}
