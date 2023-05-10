package com.robosoftin.lorem_food_app.service;

import com.robosoftin.lorem_food_app.dao.RestaurantRepository;
import com.robosoftin.lorem_food_app.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;
    public List<Restaurant> getRestaurantsByPattern(String pattern){
        String keyword=pattern.toLowerCase();
        if(keyword.equals("breakfast"))
            return restaurantRepository.findBreakfastRestaurants();
        else if(keyword.equals("lunch") || keyword.equals("dinner"))
            return restaurantRepository.findAll();
        else
            return restaurantRepository.findByPattern(pattern);
    }
}
