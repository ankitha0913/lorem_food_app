package com.robosoftin.lorem_food_app.controller;

import com.robosoftin.lorem_food_app.model.CartRequest;
import com.robosoftin.lorem_food_app.model.StatusResponse;
import com.robosoftin.lorem_food_app.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH},
        origins = {"http://localhost:8080","https://main-sphere-386011.uc.r.appspot.com"})
public class CartController {
    @Autowired
    private CartItemService cartItemService;
    @PostMapping("/add-item")
    public ResponseEntity<StatusResponse> addToCart(@RequestBody CartRequest cartRequest) {
        StatusResponse statusResponse = cartItemService.addToCartItem(cartRequest);
        return ResponseEntity.status(HttpStatus.OK).body(statusResponse);
    }
}
