package com.robosoftin.lorem_food_app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class StatusResponse {
    private boolean status;
    private int statusCode;
    private String message;

    public StatusResponse(boolean status, int statusCode, String message) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String secretCode;
}
