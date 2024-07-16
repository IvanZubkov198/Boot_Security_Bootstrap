package ru.kata.spring.boot_security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.entities.Role;
import ru.kata.spring.boot_security.entities.User;
import ru.kata.spring.boot_security.repositories.RoleRepository;
import ru.kata.spring.boot_security.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(new User());
    }

    @Override
    @Transactional
    public boolean saveUser(User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            return false;
        }
        if (!user.getRoles().isEmpty()) {
            userRepository.save(user);
        } else {
            Set<Role> roles = Stream.of(roleRepository.findById(user.getId())
                    .get()).collect(Collectors.toSet());
            user.setRoles(roles);
            userRepository.save(user);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean registerUser(User user) {
        Set<Role> roles = Stream.of(roleRepository.findById(1L)
                .get()).collect(Collectors.toSet());
        if (userRepository.findById(user.getId()).isPresent()) {
            return false;
        }

        user.setRoles(roles);
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public boolean updateUser(User user) {
        Optional<User> userFromDB = userRepository.findById(user.getId());
        if (userFromDB.isPresent()) {
            User updatedUser = userRepository.findById(user.getId()).orElse(new User());
            updatedUser.setUsername(user.getUsername());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setEmail(user.getEmail());

            if (user.getRoles() == null) {
                Set<Role> roles = Stream.of(roleRepository.findById(1L)
                        .get()).collect(Collectors.toSet());
                updatedUser.setRoles(roles);
            } else {
                updatedUser.setRoles(user.getRoles());
            }
            userRepository.save(updatedUser);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}