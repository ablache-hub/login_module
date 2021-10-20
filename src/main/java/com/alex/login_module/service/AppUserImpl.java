package com.alex.login_module.service;

import com.alex.login_module.auth.AppUser;
import com.alex.login_module.auth.Role;
import com.alex.login_module.auth.SubRequestTemplate;
import com.alex.login_module.repo.AppUserRepo;
import com.alex.login_module.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j // Log
public class AppUserImpl implements AppUserService, UserDetailsService {

    private final AppUserRepo appUserRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    //TODO Completer "loadbyusername"
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appuser = appUserRepo.findByUsername(username);
        if(appuser == null) {
            log.error("Utilisateur inexistant");
            throw new UsernameNotFoundException("Utilisateur inexistant");
        } else {
            log.info("Utilisateur {}", username);
        }
/*        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appuser.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });*/
         return new User(appuser.getUsername(), appuser.getPassword(), appuser.getAuthorities());
    }

    //TODO Ajouter controles divers
    @Override
    public AppUser saveUser(SubRequestTemplate subUser) {
        log.info("Enregistrement de l'utilisateur {}", subUser.getUsername());
        subUser.setPassword(passwordEncoder.encode(subUser.getPassword()));
        return appUserRepo.save(
                new AppUser(subUser.getUsername(), subUser.getPassword())
        );
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
