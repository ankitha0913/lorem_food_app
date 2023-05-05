package com.robosoftin.lorem_food_app.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthErrorResponse {
    private int statusCode;
    private String message;
    private long timeStamp;

}
