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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "The User API")
@RestController
@RequestMapping("/api1/v1")
@CrossOrigin
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
    @PostMapping("/movieUser")
    public ResponseEntity<String> createMovieUser(@RequestBody MovieUser movieUser){
        if(movieUser.getPassword().length()>=4&&movieUser.getPassword().length()<=12 && movieUser.getLogin().length()>=4&&movieUser.getLogin().length()<=12){
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
//    public static MovieUserDto MovieUserToMovieUserDto(MovieUser movieUser) {
//        MovieUserDto movieUserDto = new MovieUserDto();
//        movieUserDto.setUserId(movieUser.getUserId());
//        movieUserDto.setName(movieUser.getName());
//        movieUserDto.setSurName(movieUser.getSurName());
//        movieUserDto.setEmail(movieUser.getEmail());
//        movieUserDto.setTelephoneNumber(movieUser.getTelephoneNumber());
//        List<TicketDto> ticketDtos = new ArrayList<>();
//        for (Ticket ticket : movieUser.getMoviesWatched()) {
//            TicketDto ticketDto = TicketToDto(ticket);
//            ticketDtos.add(ticketDto);
//        }
//        movieUserDto.setMoviesWatched(ticketDtos);
//
//        return movieUserDto;
//    }

}