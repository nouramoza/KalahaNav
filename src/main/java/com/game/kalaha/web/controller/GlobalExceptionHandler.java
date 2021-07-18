package com.game.kalaha.web.controller;

import com.game.kalaha.web.error.BadRequestAlertException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestAlertException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestAlertException exception) {

//        String bodyOfResponse = "This should be application specific";
        ResponseEntity<Object> result = new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        return result;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherExceptions(Exception exception) {
        ResponseEntity<Object> result = new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        return result;
    }
}
