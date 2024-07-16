package ru.kata.spring.boot_security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.entities.Role;
import ru.kata.spring.boot_security.repositories.RoleRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Set<Role> findAll() {
        return new HashSet<>(roleRepository.findAll());
    }
}