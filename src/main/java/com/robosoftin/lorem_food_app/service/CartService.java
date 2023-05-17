package com.robosoftin.lorem_food_app.service;
import com.robosoftin.lorem_food_app.dao.CartRepository;
import com.robosoftin.lorem_food_app.dao.MenuItemRepository;
import com.robosoftin.lorem_food_app.dao.RestaurantRepository;
import com.robosoftin.lorem_food_app.dao.UserRepository;
import com.robosoftin.lorem_food_app.entity.Auth.UserInfo;
import com.robosoftin.lorem_food_app.entity.Cart.MyCart;
import com.robosoftin.lorem_food_app.entity.Cart.MyCartKey;
import com.robosoftin.lorem_food_app.model.StatusResponse;
import com.robosoftin.lorem_food_app.utility.IdGeneratorUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
        MyCartKey myCartKey = new MyCartKey(userRepository.findById(userId).get(), restaurantRepository.findById(restId).get());
        try {
            return cartRepository.findById(myCartKey).get();
        }catch (NoSuchElementException exception){
            return cartRepository.save(new MyCart(IdGeneratorUtility.nextId(),myCartKey));
        }
    }

    public long getCartId(UserInfo userInfo,int restId)
    {
        MyCart myCart=cartRepository.findById(new MyCartKey(userInfo,restaurantRepository.findById(restId).get())).get();
        return myCart.getCartId();
    }

    public void deleteCart(long cart_id)
    {
        cartRepository.deleteByCartId(cart_id);
    }

    public List<MyCart> getMyCart(int userId)
    {
        return cartRepository.findByUserId(userId);
    }
}
