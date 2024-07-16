package ru.kata.spring.boot_security.services;

import ru.kata.spring.boot_security.entities.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> findAll();
}