package com.alex.login_module.auth;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER) // Recup l'utilisateur ET ses rôles
    private Collection<Role> roles = new ArrayList<>();
}
