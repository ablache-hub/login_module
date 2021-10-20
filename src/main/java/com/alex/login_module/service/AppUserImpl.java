package com.alex.login_module.service;

import com.alex.login_module.auth.AppUser;
import com.alex.login_module.auth.Role;
import com.alex.login_module.repo.AppUserRepo;
import com.alex.login_module.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j // Log
public class AppUserImpl implements AppUserService, UserDetailsService {

    private final AppUserRepo appUserRepo;
    private final RoleRepo roleRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appuser = appUserRepo.findByUsername(username);
        if(appuser == null) {
            log.error("Utilisateur inexistant");
            throw new UsernameNotFoundException("Utilisateur inexistant");
        } else {
            log.info("Utilisateur {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appuser.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
         return new User(appuser.getUsername(), appuser.getPassword(), authorities);
    }

    //TODO Ajouter controles divers
    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Enregistrement de l'utilisateur {}", user.getUsername());
        return appUserRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Enregistrement du nouveau role {}", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String rolename) {
        log.info("Ajout du rôle {} pour l'utilisateur {}", rolename, username);
        AppUser appUser = appUserRepo.findByUsername(username);
        Role role = roleRepo.findByName(rolename);
        appUser.getRoles().add(role);
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Recup de l'utilisateur {}", username);
        return appUserRepo.findByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Recup de tous les utilisateurs");
        return appUserRepo.findAll();
    }
}
