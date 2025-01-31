package com.redeemerlives.customer_service.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> exception(DataIntegrityViolationException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already registered");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> exception(BadCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email or password");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> exception(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

}
