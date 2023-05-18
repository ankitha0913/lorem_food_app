package com.robosoftin.lorem_food_app.controller;

import com.robosoftin.lorem_food_app.model.OrderRequest;
import com.robosoftin.lorem_food_app.model.StatusResponse;
import com.robosoftin.lorem_food_app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH},
        origins = {"http://localhost:8080","https://main-sphere-386011.uc.r.appspot.com"})
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/place-order")
    public ResponseEntity<StatusResponse> placeOrder(@RequestBody OrderRequest orderRequest) throws Exception {
        StatusResponse statusResponse = orderService.placeOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.OK).body(statusResponse);
    }
}
