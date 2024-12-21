package org.example.services;

import org.example.modules.MovieUser;
import org.example.modules.Role;
import org.example.repositories.MovieUserRepository;
import org.example.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.acls.model.AlreadyExistsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import jakarta.persistence.*;
import java.util.Collections;

@Service
public class MovieUserServices {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public MovieUserRepository movieUserRepository;

    public Page<MovieUser> getAllUser(int page, int size){
        return movieUserRepository.findAll(PageRequest.of(page, size));
    }

    public void createUser(MovieUser movieUser) {
        MovieUser userFromDB = movieUserRepository.findByUsername(movieUser.getUsername());

        if (userFromDB != null) throw new AlreadyExistsException("User already exists");

        Role userRole = roleRepository.findByName("USER");
        if (userRole == null) {
            userRole = new Role("USER");
            roleRepository.save(userRole);
        }

        movieUser.setRoles(Collections.singleton(userRole));
        movieUserRepository.save(movieUser);
    }

    public MovieUser getUserById(long shelfId) {
        return movieUserRepository.findById(shelfId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + shelfId));
    }

    public void deleteMovieUserById(long movieUserId) {movieUserRepository.deleteById(movieUserId); }

    public MovieUser getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof MovieUser) {
                return (MovieUser) principal;
            } else if (principal instanceof org.springframework.security.core.userdetails.User) {
                String username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
                return movieUserRepository.findByUsername(username);
            }
        }
        throw new UsernameNotFoundException("User not found");
    }

    public MovieUser getUserByName(String username) {
        return movieUserRepository.findByUsername(username);
    }
}
