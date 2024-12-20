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

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpServletRequest request, Model model) {
        UserDetails userDetails = null;
        try
        {
            userDetails = userService.getCurrentAuthenticatedUser();
            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                HttpSession session = request.getSession();
                session.setAttribute("user", userDetails);
                return "redirect:/";
            }
            else
            {
                model.addAttribute("error", "Invalid password.");
                return "redirect:/login?error";
            }
        }
        catch (UsernameNotFoundException e)
        {
            model.addAttribute("error", "User not found.");
            return "redirect:/login?error";
        }
    }


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
   public RegistrationController(MovieUserServices service) {this.userService = service;}
}  