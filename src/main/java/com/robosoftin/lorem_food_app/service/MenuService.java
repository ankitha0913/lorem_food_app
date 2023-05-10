package com.robosoftin.lorem_food_app.service;

import com.robosoftin.lorem_food_app.dao.MenuItemRepository;
import com.robosoftin.lorem_food_app.entity.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MenuItemRepository menuItemRepository;
    public List<MenuItem> fetchMenu(String restaurantName){
        return menuItemRepository.findRestaurantMenu(restaurantName);
    }
}
