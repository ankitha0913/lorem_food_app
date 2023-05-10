package com.robosoftin.lorem_food_app.dao;

import com.robosoftin.lorem_food_app.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {
    @Query("SELECT r FROM Restaurant r WHERE r.name LIKE %?1%")
    List<Restaurant> findByPattern(String pattern);

    @Query("SELECT r FROM Restaurant r WHERE r.breakfastAvailable=true")
    List<Restaurant> findBreakfastRestaurants();

}
