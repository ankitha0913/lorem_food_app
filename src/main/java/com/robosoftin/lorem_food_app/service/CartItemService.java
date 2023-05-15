package com.robosoftin.lorem_food_app.service;
import com.robosoftin.lorem_food_app.dao.CartItemRepository;
import com.robosoftin.lorem_food_app.dao.CartRepository;
import com.robosoftin.lorem_food_app.dao.MenuItemRepository;
import com.robosoftin.lorem_food_app.dao.UserRepository;
import com.robosoftin.lorem_food_app.entity.Auth.UserInfo;
import com.robosoftin.lorem_food_app.entity.Cart.CartItem;
import com.robosoftin.lorem_food_app.entity.Cart.CartItemKey;
import com.robosoftin.lorem_food_app.model.CartRequest;
import com.robosoftin.lorem_food_app.model.ClearCartRequest;
import com.robosoftin.lorem_food_app.model.StatusResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CartItemService {

    final private CartItemRepository cartItemRepository;

    final private MenuItemRepository menuItemRepository;

    final private UserRepository userRepository;

    final private CartService cartService;

    final private CartRepository cartRepository;
    public StatusResponse addToCartItem(CartRequest cartRequest){
        int menuItemId=cartRequest.getMenuItemId();
        UserInfo userInfo=userRepository.findByEmailId(cartRequest.getEmailId());
        CartItemKey cartItemKey= new CartItemKey(cartService.addToCart(userInfo.getId(),menuItemId),menuItemRepository.findById(menuItemId).get());
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

    @Transactional
    public StatusResponse deleteCartItem(CartRequest cartRequest){
        int menuItemId=cartRequest.getMenuItemId();
        UserInfo userInfo=userRepository.findByEmailId(cartRequest.getEmailId());
        int restId = menuItemRepository.getRestId(menuItemId);
        long cart_id=cartService.getCartId(userInfo,restId);
        CartItem cartItem=cartItemRepository.findById(new CartItemKey(cartRepository.findByCartId(cart_id),menuItemRepository.findById(menuItemId).get())).get();
        if(cartItem.getQuantity()==1)
            cartItemRepository.deleteByCartIdAndMenuItemId(cart_id,menuItemId);
        else {
            cartItem.setQuantity(cartItem.getQuantity()-1);
            cartItemRepository.save(cartItem);
        }
        return new StatusResponse(HttpStatus.OK.value(),"Item removed from cart");
    }

    @Transactional
    public StatusResponse clearCart(ClearCartRequest clearCartRequest){
        int restId=clearCartRequest.getRestId();
        UserInfo userInfo=userRepository.findByEmailId(clearCartRequest.getEmailId());
        long cart_id=cartService.getCartId(userInfo,restId);
        cartItemRepository.deleteByCartId(cart_id);
        cartService.deleteCart(cart_id);
        return new StatusResponse(HttpStatus.OK.value(),"Cart deleted successfully");
    }
}
