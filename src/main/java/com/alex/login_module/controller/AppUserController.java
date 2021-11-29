package com.alex.login_module.controller;

import com.alex.login_module.auth.AppUser;
import com.alex.login_module.auth.Role;
import com.alex.login_module.auth.RoleToUserTemplate;
import com.alex.login_module.auth.SubRequestTemplate;
import com.alex.login_module.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok().body(appUserService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody SubRequestTemplate subUser) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(appUserService.newUser(subUser));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(appUserService.newRole(role));
    }

    @PostMapping("/role/addroleuser")
    public ResponseEntity<Void> addRoleToUser(@RequestBody RoleToUserTemplate role) {
        appUserService.addRoleToUser(role.getUsername(), role.getRoleName());
        return ResponseEntity.ok().build();
    }

}

