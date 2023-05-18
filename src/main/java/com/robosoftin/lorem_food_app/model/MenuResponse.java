package com.robosoftin.lorem_food_app.model;

import com.robosoftin.lorem_food_app.entity.Restaurant.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponse {
    private int quantity;
    private MenuItem menuItem;
}
