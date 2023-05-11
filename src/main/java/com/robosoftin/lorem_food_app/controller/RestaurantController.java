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
@CrossOrigin(
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH},
        origins = {"http://localhost:8080","https://main-sphere-386011.uc.r.appspot.com"})
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private MenuService menuService;
    @GetMapping("/search")
    public ResponseEntity<StatusResponse> search(@RequestParam String query,@RequestParam String city,@RequestParam String state,@RequestParam String country){
        List<Restaurant> restaurantList=restaurantService.getRestaurantsByPattern(query,city,state,country);
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponse(HttpStatus.OK.value(), restaurantList));
    }

    @GetMapping("/{restId}/menu")
    public ResponseEntity<StatusResponse> fetchMenu(@PathVariable int restId){
         List<MenuItem> menuItemList=menuService.fetchMenu(restId);
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponse(HttpStatus.OK.value(), menuItemList));
    }

    @GetMapping("/{restId}/menu/search")
    public ResponseEntity<StatusResponse> searchInMenu(@PathVariable int restId,@RequestParam String query){
        List<MenuItem> menuItemList=menuService.getMenuItemsByPattern(restId,query);
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponse(HttpStatus.OK.value(), menuItemList));
    }

}
