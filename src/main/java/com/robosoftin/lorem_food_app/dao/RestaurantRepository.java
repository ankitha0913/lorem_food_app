package com.robosoftin.lorem_food_app.dao;

import com.robosoftin.lorem_food_app.entity.Restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {
    @Query("SELECT r FROM Restaurant r WHERE r.name LIKE %?1% AND r.city=?2 AND r.state=?3 AND r.country=?4")
    List<Restaurant> findByPattern(String pattern,String city,String state,String country);

    @Query("SELECT r FROM Restaurant r WHERE r.breakfastAvailable=true AND r.city=?1 AND r.state=?2 AND r.country=?3")
    List<Restaurant> findBreakfastRestaurants(String city,String state,String country);

    @Query("SELECT r FROM Restaurant r WHERE r.city=?1 AND r.state=?2 AND r.country=?3")
    List<Restaurant> findAllRestaurants(String city,String state,String country);

}
