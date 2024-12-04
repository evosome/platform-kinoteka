package com.evotek.user.controllers;


import com.evotek.user.modules.MovieUser;
import com.evotek.user.services.MovieUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api1/v1")
public class MovieUserController {
    public static MovieUserServices userService;
    @Autowired
    public MovieUserController(MovieUserServices userService){
        MovieUserController.userService = userService;
    }
    @GetMapping("/movieUser")
    public List<MovieUser> getUser(){

        return userService.getAllUser();
    }
    @PostMapping("/movieUser/{historyId}")
    public ResponseEntity<String> createMovieUser(@PathVariable int historyId, @RequestBody MovieUser movieUser){
        if(movieUser.getPassword().length()>=4&&movieUser.getPassword().length()<=12 && movieUser.getLogin().length()>=4&&movieUser.getLogin().length()<=12){
            userService.createUser(movieUser);
            return ResponseEntity.ok("Ok");
        }else{
            return ResponseEntity.badRequest().body("Длина пароля или логина должна быть от 4 до 12 символов");
        }
    }
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
