package ru.kata.spring.boot_security.demo.model;

import com.sun.istack.NotNull;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @Column
    private String role;
//    @ManyToMany(mappedBy = "roles")
//    private Set<User> users;

    public Role() {

    }

    public Role(String role) {
        this.role = role;
    }

    // getters


    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

//    public Set<User> getUsers() {
//        return users;
//    }

    // setters


    public void setId(Long id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //public void setUsers(Set<User> users) {
        //this.users = users;
    //}

    @Override
    public String getAuthority() {
        return getRole();
    }
}
