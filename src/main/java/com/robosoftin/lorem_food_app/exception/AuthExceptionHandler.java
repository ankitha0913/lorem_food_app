package com.robosoftin.lorem_food_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice()
public class AuthExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AuthErrorResponse> handleException(Exception exc){
        return errorResponse(HttpStatus.BAD_REQUEST,exc.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<AuthErrorResponse> handleException(UnauthorizedException exc){
        return errorResponse(HttpStatus.UNAUTHORIZED,exc.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<AuthErrorResponse> handleException(BearerTokenNotFoundException exc){
        return errorResponse(HttpStatus.FORBIDDEN,exc.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<AuthErrorResponse> handleException(UserNotFoundException exc){
        return errorResponse(HttpStatus.NOT_FOUND,exc.getMessage());
    }

    public ResponseEntity<AuthErrorResponse> errorResponse(HttpStatus httpStatus,String errorMessage){
        AuthErrorResponse error = new AuthErrorResponse();
        error.setStatusCode(httpStatus.value());
        error.setMessage(errorMessage);
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error,httpStatus);
    }



}

