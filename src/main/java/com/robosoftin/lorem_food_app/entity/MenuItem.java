package com.robosoftin.lorem_food_app.entity;

import com.robosoftin.lorem_food_app.enums.DishType;
import com.robosoftin.lorem_food_app.enums.RestaurantStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "menu_item")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private DishType dishType;
    private boolean breakfast;
    private boolean veg;
    private double price;
    private double rating;
    @ManyToOne(targetEntity = Menu.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private Menu menu;
    private String image;
}
