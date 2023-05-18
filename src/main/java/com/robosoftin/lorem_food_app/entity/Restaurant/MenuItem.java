package com.robosoftin.lorem_food_app.entity.Restaurant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.robosoftin.lorem_food_app.entity.Restaurant.Menu;
import com.robosoftin.lorem_food_app.enums.DishType;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Menu menu;
    private String image;

    public MenuItem(int id,String name, String description, DishType dishType, boolean breakfast, boolean veg, double price, double rating, String image) {
        this.id=id;
        this.name = name;
        this.description = description;
        this.dishType = dishType;
        this.breakfast = breakfast;
        this.veg = veg;
        this.price = price;
        this.rating = rating;
        this.image = image;
    }

    public MenuItem removeMenuField()
    {
        return new MenuItem(this.id,this.name,this.description,this.dishType,this.breakfast,this.veg,this.price,this.rating,this.image);
    }
}
