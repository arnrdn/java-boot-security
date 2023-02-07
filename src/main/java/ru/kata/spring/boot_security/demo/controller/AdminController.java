package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final UserService userService;

    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String createUserForm(ModelMap model, Principal principal) {
        model.addAttribute("admin", principal);
        model.addAttribute("users", userService.findAll());
        return "admin";
    }
    @GetMapping("/user-create")
    public String createUserForm(User user, ModelMap model) {
        model.addAttribute("roles", roleService.findAll());
        return "user-create";
    }

    @PostMapping("/user-create")
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

//    @GetMapping("/user-update/{id}")
//    public String updateUserForm(@PathVariable("id") Long id, ModelMap model) {
//        User user = userService.findById(id);
//        model.addAttribute("user", user);
//        return "user-update";
//    }
//
//    @PostMapping("/user-update")
//    public String updateUser(User user) {
//        userService.saveUser(user);
//        return "redirect:/admin";
//    }

    @GetMapping("/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user-update";
    }

    @PutMapping("/user-update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }
}
