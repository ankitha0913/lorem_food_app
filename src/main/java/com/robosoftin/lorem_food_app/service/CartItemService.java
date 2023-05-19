package com.robosoftin.lorem_food_app.service;
import com.robosoftin.lorem_food_app.dao.CartItemRepository;
import com.robosoftin.lorem_food_app.dao.CartRepository;
import com.robosoftin.lorem_food_app.dao.MenuItemRepository;
import com.robosoftin.lorem_food_app.dao.UserRepository;
import com.robosoftin.lorem_food_app.entity.Auth.UserInfo;
import com.robosoftin.lorem_food_app.entity.Cart.CartItem;
import com.robosoftin.lorem_food_app.entity.Cart.CartItemKey;
import com.robosoftin.lorem_food_app.entity.Cart.MyCart;
import com.robosoftin.lorem_food_app.entity.Restaurant.MenuItem;
import com.robosoftin.lorem_food_app.model.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CartItemService {

    final private CartItemRepository cartItemRepository;

    final private MenuItemRepository menuItemRepository;

    final private UserRepository userRepository;

    final private CartService cartService;

    final private RestaurantService restaurantService;

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
    public void deleteByCartIdAndMenuItemId(long cartId,int menuItemId)
    {
        cartItemRepository.deleteByCartIdAndMenuItemId(cartId,menuItemId);
    }

    @Transactional
    public void deleteByCartId(long cartId)
    {
        cartItemRepository.deleteByCartId(cartId);
    }
    @Transactional
    public StatusResponse deleteCartItem(CartRequest cartRequest){
        int menuItemId=cartRequest.getMenuItemId();
        UserInfo userInfo=userRepository.findByEmailId(cartRequest.getEmailId());
        int restId = menuItemRepository.getRestId(menuItemId);
        long cartId=cartService.getCartId(userInfo,restId);
        CartItem cartItem=cartItemRepository.findById(new CartItemKey(cartService.findByCartId(cartId),menuItemRepository.findById(menuItemId).get())).get();
        if(cartItem.getQuantity()==1)
            deleteByCartIdAndMenuItemId(cartId,menuItemId);
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
        long cartId=cartService.getCartId(userInfo,restId);
        deleteByCartId(cartId);
        cartService.deleteCart(cartId);
        return new StatusResponse(HttpStatus.OK.value(),"Cart deleted successfully");
    }

    public StatusResponse getMyCart(String emailId)
    {
        UserInfo userInfo=userRepository.findByEmailId(emailId);
        List<MyCart> myCart=cartService.getMyCart(userInfo.getId());
        List<RestaurantResponse> restaurantResponse = new ArrayList<>();
        for (MyCart cart:myCart)
        {
            List<CartItem> cartItems=findByCartId(cart.getCartId());
            restaurantResponse.add(new RestaurantResponse(cart.getMyCartKey().getRestaurant(),getMenuResponse(cartItems),cart.getCartId()));
        }
        return new StatusResponse(HttpStatus.OK.value(), restaurantResponse);
    }

    public StatusResponse getMyRestCart(String emailId,int restId)
    {
        UserInfo userInfo=userRepository.findByEmailId(emailId);
        MyCart myCart=cartService.getCartByRestIdAndUserId(restId,userInfo.getId());
        RestaurantResponse restaurantResponse = new RestaurantResponse();
        if(myCart!=null)
        {
            List<CartItem> cartItems=findByCartId(myCart.getCartId());
            restaurantResponse= new RestaurantResponse(myCart.getMyCartKey().getRestaurant(),getMenuResponse(cartItems),myCart.getCartId());
        }
        else
            restaurantResponse=new RestaurantResponse(new ArrayList<>());
        return new StatusResponse(HttpStatus.OK.value(),restaurantResponse);
    }

    private List<MenuResponse> getMenuResponse(List<CartItem> cartItems)
    {
        List<MenuResponse> menuResponse=new ArrayList<>();
        for (CartItem cartItem:cartItems)
        {
            int quantity=cartItem.getQuantity();
            MenuItem menuItem=cartItem.getCartItemKey().getMenuItem();
            menuResponse.add(new MenuResponse(quantity,menuItem.removeMenuField()));
        }
        return menuResponse;
    }

    public List<CartItem> findByCartId(long cartId)
    {
        return cartItemRepository.findByCartId(cartId);
    }
}
