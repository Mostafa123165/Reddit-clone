package com.practice.reddit.Exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<?> notFoundExc(CustomNotFoundException notFoundException) {
        ErrorDetails notFoundExc = new ErrorDetails();
        notFoundExc.setStatus(HttpStatus.NOT_FOUND.value());
        notFoundExc.setMessage(notFoundException.getMessage());

        return new ResponseEntity<>(notFoundExc, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> notValidData(MethodArgumentNotValidException validException) {
        ErrorDetails notFoundExc = new ErrorDetails();
        StringBuilder message = new StringBuilder();

        List<String> errors = validException.getBindingResult().getAllErrors().stream().map(
                DefaultMessageSourceResolvable::getDefaultMessage).toList();

        for(String error : errors) {
            message.append(error + " , ");
        }

        notFoundExc.setMessage(message.toString());
        notFoundExc.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(notFoundExc, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
        public ResponseEntity<?> exception(Exception exception) {
        ErrorDetails notFoundExc = new ErrorDetails();
        notFoundExc.setStatus(HttpStatus.BAD_REQUEST.value());
        notFoundExc.setMessage(exception.getMessage());

        return new ResponseEntity<>(notFoundExc, HttpStatus.BAD_REQUEST);
    }
}
