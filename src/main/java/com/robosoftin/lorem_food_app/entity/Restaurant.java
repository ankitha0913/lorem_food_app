package com.robosoftin.lorem_food_app.entity;

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
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String country;
    private double rating;
    @Enumerated(EnumType.STRING)
    private RestaurantStatus status;
    private String openTime;
    private String closeTime;
    private String image;
    private boolean breakfastAvailable;
}

