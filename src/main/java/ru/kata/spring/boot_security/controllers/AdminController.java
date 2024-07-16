package ru.kata.spring.boot_security.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.entities.User;
import ru.kata.spring.boot_security.configs.security.UserDetailsImp;
import ru.kata.spring.boot_security.services.RoleService;
import ru.kata.spring.boot_security.services.UserServiceImp;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserServiceImp userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @GetMapping()
    public String getAllUsers(@ModelAttribute("user") User user, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();

        model.addAttribute("userFromDetails", userDetails.getUser());
        model.addAttribute("allRoles", roleService.findAll());
        model.addAttribute("users", userService.findAll());
        return "admin";
    }


    @PostMapping()
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (!userService.saveUser(user)) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "admin";
        }
        return "redirect:/admin";
    }

    @PutMapping()
    public String updateUser(@ModelAttribute("user") User user, Model model) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (!userService.updateUser(user)) {
            model.addAttribute("updateUserError", "Не удалось обновить пользователя");
        }
        return "redirect:/admin";
    }

    @DeleteMapping()
    public String deleteUserById(@RequestParam(value = "id") long id, Model model) {
        if (!userService.deleteById(id)) {
            model.addAttribute("deleteUserError", "Не удалось удалить пользователя");
        }
        return "redirect:/admin";
    }

}