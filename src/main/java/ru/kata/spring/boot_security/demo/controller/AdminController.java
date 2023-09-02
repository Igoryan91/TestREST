package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.exception_handing.NoSuchUserException;
import ru.kata.spring.boot_security.demo.exception_handing.UserIncorrectData;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin")
//@CrossOrigin
public class AdminController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final RoleService roleService;
    private static final String REDIRECT = "redirect:";

    @Autowired
    public AdminController(UserService userService, UserValidator userValidator, RoleService roleService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.roleService = roleService;
    }

//    @GetMapping()
//    public String adminPage(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userSecurity = (UserDetails) authentication.getPrincipal();
//        model.addAttribute("user", userService.getUser(userSecurity.getId()));
//        model.addAttribute("users", userService.getAllUsers());
//        model.addAttribute("roles", roleService.addAllRoles());
//        return "admin/adminPage";
//    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> showAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getOneUser(@PathVariable("id") int id) {
        return new ResponseEntity<>(userService.getUser(id).orElseThrow(NoSuchUserException::new),
                HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<HttpStatus> addNewUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user, @PathVariable("id") int id) {
        userService.updateUser(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

//    @GetMapping("/new")
//    public String createPage(@ModelAttribute("user") User user, Principal principal,
//                             Model model) {
//        model.addAttribute("authUser", userService.getUser(principal.getName()));
//        model.addAttribute("roles", roleService.addAllRoles());
//        return "admin/new";
//    }
//
//    @PostMapping("/save")
//    public String saveNewUser(@ModelAttribute("user") @Valid User user, Principal principal,
//                              Model model, BindingResult bindingResult) {
//        model.addAttribute("authUser", userService.getUser(principal.getName()));
//        userValidator.validate(user, bindingResult);
//        if (bindingResult.hasErrors())
//            return "admin/new";
//        userService.saveUser(user);
//        return REDIRECT;
//    }
//
//    @PatchMapping("/{username}")
//    public String saveUpdatedUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
//                                  @PathVariable("username") String username, Model model) {
//        if (!user.getUsername().equals(username)) {
//            userValidator.validate(user, bindingResult);
//        }
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("user", userService.getUser(username));
//            return REDIRECT;
//        }
//        userService.updateUser(username, user);
//        return REDIRECT;
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteUser(@PathVariable("id") int id) {
//        userService.removeUser(id);
//        return REDIRECT;
//    }
}
