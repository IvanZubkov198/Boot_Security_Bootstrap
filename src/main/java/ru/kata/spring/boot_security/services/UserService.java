package ru.kata.spring.boot_security.services;

import ru.kata.spring.boot_security.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    User findById(Long Id);

    Optional<User> findByUsername(String username);

    boolean saveUser(User user);

    boolean registerUser(User user);

    boolean updateUser(User user);

    boolean deleteById(Long id);
}