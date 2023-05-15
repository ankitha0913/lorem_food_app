package com.robosoftin.lorem_food_app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String jwtToken;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String refreshToken;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String,String> userDetails;

    public JwtResponse(String jwtToken, String message) {
        this.jwtToken = jwtToken;
        this.message = message;
    }
}
