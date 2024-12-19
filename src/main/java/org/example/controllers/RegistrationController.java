package org.example.controllers;

import org.example.modules.MovieUser;
import org.example.services.MovieUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api1/v1")
@CrossOrigin
public class RegistrationController {

    @Autowired
    private MovieUserServices userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm() {
        System.out.println("Showing registration form");//для проверки
        return "redirect:/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("movieUser") MovieUser movieUser) {
        movieUser.setPassword(passwordEncoder.encode(movieUser.getPassword()));
        userService.createUser(movieUser);
        return "redirect:/login";
    }
}  