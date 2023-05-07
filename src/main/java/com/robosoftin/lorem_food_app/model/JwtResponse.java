package com.robosoftin.lorem_food_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String jwtToken;
    private String refreshToken;
    private String message;
    private Map<String,String> userDetails;
}
