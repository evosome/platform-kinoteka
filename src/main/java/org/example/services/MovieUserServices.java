package org.example.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.modules.HallLayout;
import org.example.modules.MovieUser;
import org.example.modules.Role;
import org.example.repositories.MovieUserRepository;
import org.example.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieUserServices implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MovieUserRepository movieUserRepository;
    @Autowired
    public MovieUserServices(MovieUserRepository movieUserRepository){this.movieUserRepository = movieUserRepository;}
    public Page<MovieUser> getAllUser(int page, int size){
        return movieUserRepository.findAll(PageRequest.of(page, size));
    }
    public boolean createUser(MovieUser movieUser){
        MovieUser userFromDB = movieUserRepository.findByUsername(movieUser.getUsername());
        if (userFromDB != null) {
            return false;
        }
        movieUser.setRoles(Collections.singleton(new Role(1L, "USER")));
        movieUser.setPassword(bCryptPasswordEncoder.encode(movieUser.getPassword()));
        movieUserRepository.save(movieUser);
        return true;
    }
    public MovieUser getUserById(long shelfId) {
        return movieUserRepository.findById(shelfId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + shelfId));
    }
    public void deleteMovieUserById(long movieUserId) {movieUserRepository.deleteById(movieUserId); }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MovieUser user = movieUserRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
