package org.example.error;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class AuthAdvice {

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseStatusException handle(AuthenticationException e) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

}
