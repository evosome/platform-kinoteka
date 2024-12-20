package org.example.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.WebSecurityConfig;
import org.example.modules.MovieUser;
import org.example.services.MovieUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
public class RegistrationController {

    @Autowired
    private MovieUserServices userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm() {
        System.out.println("Showing registration form"); // Для проверки
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("movieUser") MovieUser movieUser) {
        passwordEncoder = WebSecurityConfig.passwordEncoder();
        movieUser.setPassword(passwordEncoder.encode(movieUser.getPassword()));

        boolean isRegistered = userService.createUser(movieUser);

        if (isRegistered) {
            System.out.println("User registered successfully");
            return "redirect:/login";
        } else {
            System.out.println("Username already exists");
            return "redirect:/register";
        }
    }

    //    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
   public RegistrationController(MovieUserServices service) {this.userService = service;}


//    @GetMapping("/register")
//    public String register(){
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String registerPlayer(MovieUser user) {
//        if (userService.createUser(user))
//        {
//            return "redirect:/login";
//        }
//        return "redirect:/register?error";
//    }
}  