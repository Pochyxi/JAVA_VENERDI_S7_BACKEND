package com.example.java_venerdi_s7.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sonde")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Sonda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeSonda;

    private double latitudine;

    private double longitudine;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @ManyToMany
    @JoinTable(name = "sonda_roles",
            joinColumns = @JoinColumn(name = "sonda_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<Role>();

    private Boolean active = true;

    public Sonda(String nomeSonda, String username, String password) {

        this.nomeSonda = nomeSonda;
        this.username = username;
        this.password = password;
    }

    public void addRole(Role r) {
        this.roles.add(r);
    }
}
