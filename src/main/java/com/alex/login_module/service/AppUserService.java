package com.alex.login_module.service;

import com.alex.login_module.auth.AppUser;
import com.alex.login_module.auth.Role;
import com.alex.login_module.auth.SubRequestTemplate;

import java.util.List;

public interface AppUserService {
    AppUser newUser(SubRequestTemplate subUser);
    Role newRole(Role role);
    void addRoleToUser(String username, String rolename);
    AppUser getUser(String username);
    List<AppUser> getUsers();

}
