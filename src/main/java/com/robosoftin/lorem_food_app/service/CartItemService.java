package com.robosoftin.lorem_food_app.service;

import com.robosoftin.lorem_food_app.dao.CartItemRepository;
import com.robosoftin.lorem_food_app.dao.CartRepository;
import com.robosoftin.lorem_food_app.dao.MenuItemRepository;
import com.robosoftin.lorem_food_app.dao.UserRepository;
import com.robosoftin.lorem_food_app.entity.Auth.UserInfo;
import com.robosoftin.lorem_food_app.entity.Cart.CartItem;
import com.robosoftin.lorem_food_app.entity.Cart.CartItemKey;
import com.robosoftin.lorem_food_app.entity.Cart.MyCart;
import com.robosoftin.lorem_food_app.model.CartRequest;
import com.robosoftin.lorem_food_app.model.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartService cartService;
    public StatusResponse addToCartItem(CartRequest cartRequest){
        int menuItemId=cartRequest.getMenuItemId();
        UserInfo userInfo=userRepository.findByEmailId(cartRequest.getEmailId());
        CartItemKey cartItemKey=new CartItemKey();
        cartItemKey.setMenuItem(menuItemRepository.findById(menuItemId).get());
        cartItemKey.setMyCart(cartService.addToCart(userInfo.getId(),menuItemId));
        try{
            CartItem cartItem=cartItemRepository.findById(cartItemKey).get();
            cartItem.setQuantity(cartItem.getQuantity()+1);
            cartItemRepository.save(cartItem);
        }
        catch (NoSuchElementException exception){
            cartItemRepository.save(new CartItem(cartItemKey,1));
        }
        return new StatusResponse(HttpStatus.OK.value(),"Item added to cart");
    }
}
