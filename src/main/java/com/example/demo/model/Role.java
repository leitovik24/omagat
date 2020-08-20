package com.example.demo.model;

import com.example.demo.model.User;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    public Role() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    String role;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;


    public Role(int id, String role, Set<User> users) {
        this.id = id;
        this.role = role;
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return  role ;

    }

    @Override
    public String getAuthority() {
        return role;
    }
}