package com.robosoftin.lorem_food_app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.robosoftin.lorem_food_app.entity.Restaurant.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Restaurant restaurant;
    private List data;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long cartId;

    public RestaurantResponse(Restaurant restaurant, List data) {
        this.restaurant = restaurant;
        this.data = data;
    }

    public RestaurantResponse(List data) {
        this.data = data;
    }
}
