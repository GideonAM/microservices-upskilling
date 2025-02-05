package com.redeemerlives.notification_service.exception;

import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<String> handleException(MessagingException exception) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(exception.getMessage());
    }
}
