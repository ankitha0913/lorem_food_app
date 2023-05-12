package com.robosoftin.lorem_food_app.entity.Cart;

import com.robosoftin.lorem_food_app.entity.Restaurant.MenuItem;
import com.robosoftin.lorem_food_app.entity.Restaurant.Restaurant;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;
@Data
@Embeddable
public class CartItemKey implements Serializable {
    @ManyToOne(targetEntity = MyCart.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id_fk", referencedColumnName = "cart_id")
    private MyCart myCart;
    @ManyToOne(targetEntity = MenuItem.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_item_id", referencedColumnName = "id")
    private MenuItem menuItem;
}
