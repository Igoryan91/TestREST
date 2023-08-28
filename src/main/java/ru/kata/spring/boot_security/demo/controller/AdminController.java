package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private static final String REDIRECT = "redirect:users";

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String adminPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userSecurity = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", userService.getUser(userSecurity.getUsername()));
        return "admins/admin";
    }

    @GetMapping("/users")
    public String usersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admins/users";
    }

    @GetMapping("/{username}")
    public String editPage(Model model, @PathVariable("username") String username) {
        model.addAttribute("user", userService.getUser(username));
        return "admins/update";
    }

    @PatchMapping("/{username}")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                       @PathVariable("username") String username) {
        if (bindingResult.hasErrors())
            return "admins/update";
        user.setPassword(userService.getUser(username).getPassword());
        user.setRoles(userService.getUser(username).getRoles());
        userService.updateUser(userService.getUser(username).getId(), user);
        return REDIRECT;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return REDIRECT;
    }
}
