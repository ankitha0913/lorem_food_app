package com.robosoftin.lorem_food_app.exception;

public class BearerTokenNotFoundException extends UnauthorizedException{
    public BearerTokenNotFoundException(String msg) {
        super(msg);
    }

    public BearerTokenNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
