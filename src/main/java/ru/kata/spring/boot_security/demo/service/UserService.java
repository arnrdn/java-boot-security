package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.DAO.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }


    public void saveUser(User user) {
        User userDB = userRepository.findByUsername(user.getUsername());

        if (userDB == null) {
            User userToSave = new User(user.getUsername(), user.getName(), user.getLastName(), user.getFavouriteColor(), passwordEncoder().encode(user.getPassword()), user.getRoles());
            userRepository.save(userToSave);
        }

    }

    public void updateUser(User user) {
        User userDB = findById(user.getId());

        if (passwordEncoder().matches(user.getPassword(), userDB.getPassword())) {
            user.setPassword(userDB.getPassword());
        } else {
            user.setPassword(user.getPassword());
        }
//        user.setUsername(user.getUsername());
//        user.setName(user.getUsername());
//        user.setLastName(user.getLastName());
//        user.setFavouriteColor(user.getFavouriteColor());
//        user.setRoles(user.getRoles());
        userRepository.save(user);
    }



    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toSet());
    }
}
