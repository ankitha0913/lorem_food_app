package com.robosoftin.lorem_food_app.service;

import com.robosoftin.lorem_food_app.dao.RestaurantRepository;
import com.robosoftin.lorem_food_app.entity.Restaurant.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;
    public List<Restaurant> getRestaurantsByPattern(String pattern,String city,String state,String country){
        String keyword=pattern.toLowerCase();
        if(keyword.equals("breakfast"))
            return restaurantRepository.findBreakfastRestaurants(city,state,country);
        else if(keyword.equals("lunch") || keyword.equals("dinner"))
            return restaurantRepository.findAllRestaurants(city,state,country);
        else
            return restaurantRepository.findByPattern(pattern,city,state,country);
    }
}
