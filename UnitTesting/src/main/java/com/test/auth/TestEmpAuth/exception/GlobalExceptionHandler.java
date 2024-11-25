package com.test.auth.TestEmpAuth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidRequestException(InvalidRequestException exception)
    {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), exception.getMessage(), exception.getErrorType());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoHandlerFoundException(NoHandlerFoundException exception)
    {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), "URL requested does not exist", "Incorrect URL");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException exception)
    {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), exception.getMessage(), exception.getErrorType());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }



}
