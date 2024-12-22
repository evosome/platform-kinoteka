package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.*;
import org.example.modules.MovieUser;
import org.example.services.MovieUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "The User API")
@RestController
@RequestMapping("/api1/v1")
public class MovieUserController {
    public static MovieUserServices userService;
    @Autowired
    public MovieUserController(MovieUserServices userService){
        MovieUserController.userService = userService;
    }
    @Operation(summary = "Gets all users", tags = "user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the users",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = MovieUser.class)))
                    })
    })
    @GetMapping("/movieUser")
    public Page<MovieUser> getUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return userService.getAllUser(page, size);
    }
    @Operation(summary = "Create new users", tags = "user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "add new user",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = MovieUser.class)))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/movieUser")
    public ResponseEntity<String> createMovieUser(@RequestBody MovieUser movieUser){
        if(movieUser.getPassword().length()>=4&&movieUser.getPassword().length()<=12 && movieUser.getUsername().length()>=4&&movieUser.getUsername().length()<=12){
            movieUser.addRole(movieUser);
            userService.createUser(movieUser);
            return ResponseEntity.ok("Ok");
        }else{
            return ResponseEntity.badRequest().body("Длина пароля или логина должна быть от 4 до 12 символов");
        }
    }
    @Operation(summary = "Delete user by id", tags = "user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "delete user",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = MovieUser.class)))
                    })
    })
    @DeleteMapping("/movieUser/{id}")
    public void deleteMovieUserById(@PathVariable Long id){
        userService.deleteMovieUserById(id);
    }
    @Operation(summary = "Get user by id", tags = "user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "get user",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = MovieUser.class)))
                    })
    })
    @GetMapping("/movieUser/{id}")
    public MovieUser getMovieUserById(@PathVariable long id){
        return userService.getUserById(id);
    }

    @PreAuthorize("isFullyAuthenticated()")
    @GetMapping("/movieUser/me")
    public MovieUser getCurrentMovieUser(
            @AuthenticationPrincipal MovieUser user
    ) {
        return userService.getCurrentAuthenticatedUser();
    }
}