package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/auth/login")
//    public String loginPage() {
//        return "user/login";
//    }
//
//    @GetMapping("/user")
//    public String userPage(Model model, Principal principal) {
//        model.addAttribute("user", userService.getUser(principal.getName()));
//        return "user/userPage";
//    }
}
