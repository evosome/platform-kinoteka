package org.example.services;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import org.example.modules.MovieUser;
import org.example.modules.Role;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.core.MediaType;

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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class MovieUserServices implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final String SERVER_URL = "http://localhost:8000/api1/v1";
    @Autowired
    public MovieUserRepository movieUserRepository;
    @Autowired
    public MovieUserServices(MovieUserRepository movieUserRepository){this.movieUserRepository = movieUserRepository;}
    public Page<MovieUser> getAllUser(int page, int size){
        return movieUserRepository.findAll(PageRequest.of(page, size));
    }
    public boolean createUser(MovieUser movieUser) {
        MovieUser userFromDB = movieUserRepository.findByUsername(movieUser.getUsername());
        if (userFromDB != null) {
            return false;
        }

        Role userRole = roleRepository.findByName("USER");
        if (userRole == null) {
            userRole = new Role("USER");
            roleRepository.save(userRole);
        }

        movieUser.setRoles(Collections.singleton(userRole));
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
    public static Role getRoleByIdRequest(Client client, long roleId) {
        Response response = client.target(SERVER_URL + "/roles/" + roleId)
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return response.readEntity(Role.class);
        } else {
            String errorMessage = response.readEntity(String.class);
            throw new RuntimeException("Failed to get role: HTTP error code " + response.getStatus() + ", message: " + errorMessage);
        }
    }
}
