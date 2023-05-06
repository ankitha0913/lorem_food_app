package com.robosoftin.lorem_food_app.exception;

import io.jsonwebtoken.JwtException;

public class BearerTokenNotFoundException extends JwtException {
    public BearerTokenNotFoundException(String msg) {
        super(msg);
    }

    public BearerTokenNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
