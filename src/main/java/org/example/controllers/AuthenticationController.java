package org.example.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.websocket.AuthenticationException;
import org.example.config.WebSecurityConfig;
import org.example.modules.MovieUser;
import org.example.services.MovieUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthenticationController {

    @Autowired
    private MovieUserServices userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request
    ) throws AuthenticationException {

        UserDetails userDetails = userService.getUserByName(username);

        if (userDetails == null)
            throw new AuthenticationException("User not found");

        if (!passwordEncoder.matches(password, userDetails.getPassword()))
            throw new AuthenticationException("Password not matches");

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession();
        session.setAttribute("user", userDetails);

        return ResponseEntity.ok(userDetails);

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody MovieUser movieUser) {

        movieUser.setPassword(passwordEncoder.encode(movieUser.getPassword()));
        userService.createUser(movieUser);

        return ResponseEntity.ok().build();
    }
}  