package com.robosoftin.lorem_food_app.entity.Restaurant;

import com.robosoftin.lorem_food_app.entity.Restaurant.Restaurant;
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
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToOne(targetEntity = Restaurant.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "rest_id", referencedColumnName = "id")
    private Restaurant restaurant;
}
