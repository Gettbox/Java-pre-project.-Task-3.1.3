package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class InitialDataRunner implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public void run(String... args) {

        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");

        roleService.save(role1);
        roleService.save(role2);

        Set<Role> u1role = new LinkedHashSet<>();
        u1role.add(role1);
        Set<Role> u2role = new LinkedHashSet<>();
        u2role.add(role2);

        User user1 = new User("Admin_firstName","Admin_lastName", 30, "admin@mail.ru", "admin", u1role);
        User user2 = new User("User_firstName","User_lastName", 20, "user@mail.ru", "user", u2role);

        userService.save(user1);
        userService.save(user2);
    }
}