package com.robosoftin.lorem_food_app.controller;
import com.robosoftin.lorem_food_app.entity.MenuItem;
import com.robosoftin.lorem_food_app.entity.Restaurant;
import com.robosoftin.lorem_food_app.model.StatusResponse;
import com.robosoftin.lorem_food_app.service.MenuService;
import com.robosoftin.lorem_food_app.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private MenuService menuService;
    @GetMapping("/search")
    public ResponseEntity<StatusResponse> search(@RequestParam String query){
        List<Restaurant> restaurantList=restaurantService.getRestaurantsByPattern(query);
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponse(HttpStatus.OK.value(), restaurantList));
    }

    @GetMapping("/{restaurantName}")
    public ResponseEntity<StatusResponse> fetchMenu(@PathVariable String restaurantName){
         List<MenuItem> menuItemList=menuService.fetchMenu(restaurantName);
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponse(HttpStatus.OK.value(), menuItemList));
    }

//    @GetMapping("/{restaurantName}/search")
//    public ResponseEntity<StatusResponse> search(@PathVariable String restaurantName,@RequestParam String query){
//
//    }

}
