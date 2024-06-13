package com.zkt.zktspringjpa.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.zkt.zktspringjpa.model.response.Response;
import com.zkt.zktspringjpa.security.jwt.JwtAuthenticationFilter;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    // bad credentials
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {

        Response<Object> errorMessage = Response.builder()
                .success(false)
                .message("Invalid username or password")
                .data(null)
                .build();

        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {

        Response<Object> errorMessage = Response.builder()
                .success(false)
                .message("Method not allowed")
                .data(null)
                .build();

        return new ResponseEntity<>(errorMessage, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NoResourceFoundException ex) {

        Response<Object> errorMessage = Response.builder()
                .success(false)
                .message("Resource Not Found")
                .data(null)
                .build();

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleIllegalArgumentException(HttpMessageNotReadableException ex) {

        // log the error
        logger.error("Bad Request: {}", ex.getMessage());

        Response<Object> errorMessage = Response.builder()
                .success(false)
                .message("Bad Request")
                .data(null)
                .build();

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<?> handleException(Exception ex) {

    // // log the error
    // logger.error("Internal Server Error: {}", ex.getMessage());

    // Response<Object> errorMessage = Response.builder()
    // .success(false)
    // .message("Internal Server Error")
    // .data(null)
    // .build();

    // return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    // }
}
