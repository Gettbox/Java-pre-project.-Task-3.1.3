package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping
    public String showAdminPage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("authorizedUser", user);
        model.addAttribute("roles", user.getRoles());
        model.addAttribute("userList", userService.getDemandedUsers());
        model.addAttribute("rolesList", roleService.getDemandedRoles());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("newUser") User user,
                           @RequestParam(value = "rolesIdArr", required = false) int[] rolesIdArr) {
        User updatedUser = userService.setRolesToUser(user, rolesIdArr);
        userService.save(updatedUser);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @PathVariable("id") Integer id,
                             @RequestParam(value = "rolesIdArr", required = false) int[] rolesIdArr) {
        User updatedUser = userService.setRolesToUser(user, rolesIdArr);
        userService.update(id, updatedUser);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}