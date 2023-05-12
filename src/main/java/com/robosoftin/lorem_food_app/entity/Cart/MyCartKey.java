package com.robosoftin.lorem_food_app.entity.Cart;
import com.robosoftin.lorem_food_app.entity.Auth.UserInfo;
import com.robosoftin.lorem_food_app.entity.Restaurant.Restaurant;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MyCartKey implements Serializable {
    @ManyToOne(targetEntity = UserInfo.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo userInfo;
    @ManyToOne(targetEntity = Restaurant.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "rest_id", referencedColumnName = "id")
    private Restaurant restaurant;
}
