package com.robosoftin.lorem_food_app.dao;

import com.robosoftin.lorem_food_app.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem,Integer> {

    @Query("SELECT m FROM MenuItem m WHERE m.menu.restaurant.name=?1")
    List<MenuItem> findRestaurantMenu(String restaurantName);
}
