package com.robosoftin.lorem_food_app.service;

import com.robosoftin.lorem_food_app.dao.CartRepository;
import com.robosoftin.lorem_food_app.dao.MenuItemRepository;
import com.robosoftin.lorem_food_app.dao.RestaurantRepository;
import com.robosoftin.lorem_food_app.dao.UserRepository;
import com.robosoftin.lorem_food_app.entity.Cart.MyCart;
import com.robosoftin.lorem_food_app.entity.Cart.MyCartKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    public MyCart addToCart(int userId, int menuItemId) {
        int restId = menuItemRepository.getRestId(menuItemId);
        System.out.println(restId+userId);
        MyCartKey myCartKey = new MyCartKey(userRepository.findById(userId).get(), restaurantRepository.findById(restId).get());
        return cartRepository.save(new MyCart(myCartKey));
    }
}
