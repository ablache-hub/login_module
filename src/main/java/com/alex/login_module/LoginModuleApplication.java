package com.alex.login_module;

import com.alex.login_module.auth.Role;
import com.alex.login_module.auth.SubRequestTemplate;
import com.alex.login_module.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LoginModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginModuleApplication.class, args);
    }

    @Bean
    CommandLineRunner run(AppUserService appUserService) {
        return args -> {
            appUserService.newRole(new Role(null, "ROLE_USER"));
            appUserService.newRole(new Role(null, "ROLE_ADMIN"));

            appUserService.newUser(new SubRequestTemplate("test@gmail.com", "test" ));
            appUserService.newUser(new SubRequestTemplate("test2@gmail.com", "test" ));

            appUserService.addRoleToUser("test2@gmail.com", "ROLE_ADMIN");

        };
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
