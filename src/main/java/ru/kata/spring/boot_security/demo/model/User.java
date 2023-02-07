package ru.kata.spring.boot_security.demo.model;

import com.sun.istack.NotNull;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    private Long id;

    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "lastName")
    @NotNull
    private String lastName;

    @Column(name = "favouriteColor")
    @NotNull
    private String favouriteColor;

    @Column(name = "password")
    private String password;

    @Column(name="roles")
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @Fetch(FetchMode.JOIN)
    private Set<Role> roles = new HashSet<>();


    public User() {
    }

    public User(String username, String name, String lastName, String favouriteColor, String password, Set<Role> roles) {
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.favouriteColor = favouriteColor;
        this.password = password;
        this.roles = roles;
    }



    // getters

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFavouriteColor() {
        return favouriteColor;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }


    // setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFavouriteColor(String favouriteColor) {
        this.favouriteColor = favouriteColor;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", favouriteColor='" + favouriteColor + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}