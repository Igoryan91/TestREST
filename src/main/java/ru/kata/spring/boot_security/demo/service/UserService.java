package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    void saveUser(User user);

    Optional<User> getUser(int id);

    List<User> getAllUsers();

    void updateUser(int id, User updatedUser);

    void removeUser(int id);
}
