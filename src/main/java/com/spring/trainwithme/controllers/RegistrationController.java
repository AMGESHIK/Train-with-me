package com.spring.trainwithme.controllers;

import com.spring.trainwithme.domain.Role;
import com.spring.trainwithme.domain.User;
import com.spring.trainwithme.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration() {

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, Map<String, Object> model) {

        User UsernameOfUserFromDb = userRepo.findByUsername(user.getUsername());
        User EmailOfUserFromDb = userRepo.findByEmail(user.getEmail());

        if (UsernameOfUserFromDb != null) {
            model.put("message", "такой пользователь уже существует!");
            return "registration";
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.put("message", "Пароль не подтвержден!");
            return "registration";
        }
        if (EmailOfUserFromDb != null) {
            model.put("message", "Пользователь с такой почтой уже существует!");
            return "registration";
        }


        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        return "redirect:/login";
    }
}
