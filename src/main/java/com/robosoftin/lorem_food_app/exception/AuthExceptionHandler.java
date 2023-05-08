package com.robosoftin.lorem_food_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice()
public class AuthExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AuthErrorResponse> handleException(Exception exc){
        String exceptionType=exc.getClass().getName().substring(40);
        HttpStatus httpStatus=getStatus(exceptionType);
        AuthErrorResponse error = new AuthErrorResponse();
        error.setStatusCode(httpStatus.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error,httpStatus);
    }

    private HttpStatus getStatus(String exceptionType){
        if(exceptionType.equals("UnauthorizedException"))
            return HttpStatus.UNAUTHORIZED;
        else if(exceptionType.equals("BearerTokenNotFoundException"))
            return HttpStatus.FORBIDDEN;
        else if(exceptionType.equals("UserNotFoundException"))
            return HttpStatus.NOT_FOUND;
        else
            return HttpStatus.BAD_REQUEST;
    }
}

