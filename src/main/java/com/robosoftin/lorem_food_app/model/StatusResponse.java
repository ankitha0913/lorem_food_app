package com.robosoftin.lorem_food_app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class StatusResponse {
    private int statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RestaurantResponse menu;

    public StatusResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public StatusResponse(int statusCode, List data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public StatusResponse(int statusCode, RestaurantResponse restaurantResponse) {
        this.statusCode = statusCode;
        this.menu = restaurantResponse;
    }

    public StatusResponse(int statusCode, String message, String secretCode) {
        this.statusCode = statusCode;
        this.message = message;
        this.secretCode = secretCode;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String secretCode;
}
