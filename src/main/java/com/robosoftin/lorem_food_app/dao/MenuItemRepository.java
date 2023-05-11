package com.robosoftin.lorem_food_app.dao;

import com.robosoftin.lorem_food_app.entity.MenuItem;
import com.robosoftin.lorem_food_app.entity.Restaurant;
import com.robosoftin.lorem_food_app.enums.DishType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem,Integer> {

    @Query("SELECT new MenuItem(m.id,m.name,m.description,m.dishType,m.breakfast,m.veg,m.price,m.rating,m.image) FROM MenuItem m WHERE m.menu.restaurant.id=?1")
    List<MenuItem> findRestaurantMenu(int restId);

    @Query("SELECT new MenuItem(m.id,m.name,m.description,m.dishType,m.breakfast,m.veg,m.price,m.rating,m.image) FROM MenuItem m WHERE m.menu.restaurant.id=?1 AND m.breakfast=true")
    List<MenuItem> findBreakfastMenu(int restId);

    @Query("SELECT new MenuItem(m.id,m.name,m.description,m.dishType,m.breakfast,m.veg,m.price,m.rating,m.image) FROM MenuItem m WHERE m.menu.restaurant.id=?1 AND m.name LIKE %?2%")
    List<MenuItem> findByPattern(int restId,String pattern);

    @Query("SELECT new MenuItem(m.id,m.name,m.description,m.dishType,m.breakfast,m.veg,m.price,m.rating,m.image) FROM MenuItem m WHERE m.menu.restaurant.id=?1 AND m.dishType=?2")
    List<MenuItem> findByDishType(int restId,DishType dishType);

    @Query("SELECT new MenuItem(m.id,m.name,m.description,m.dishType,m.breakfast,m.veg,m.price,m.rating,m.image) FROM MenuItem m WHERE m.menu.restaurant.id=?1 AND m.veg=?2")
    List<MenuItem> findByCategory(int restId,boolean isVeg);

    @Query("SELECT new MenuItem(m.id,m.name,m.description,m.dishType,m.breakfast,m.veg,m.price,m.rating,m.image) FROM MenuItem m WHERE m.menu.restaurant.id=?1 AND m.dishType=?2 AND m.veg=?3")
    List<MenuItem> findByDishTypeAndCategory(int restId,DishType dishType,boolean isVeg);

}
