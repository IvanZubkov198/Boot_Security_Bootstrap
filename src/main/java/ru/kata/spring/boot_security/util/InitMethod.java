package ru.kata.spring.boot_security.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.entities.Role;
import ru.kata.spring.boot_security.entities.User;
import ru.kata.spring.boot_security.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InitMethod {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void postConstruct() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        roles.add(new Role("ROLE_ADMIN"));

        List<User> users = new ArrayList<>();

        users.add(new User("admin"
                , passwordEncoder.encode("admin")
                , "admin@gmail.com"
                , roles));
        users.add(new User("user"
                , passwordEncoder.encode("user")
                , "user@gmail.com"
                , roles.stream().findFirst().stream().collect(Collectors.toSet())));

        userRepository.saveAll(users);
    }
}
