package com.robosoftin.lorem_food_app.entity.Cart;

import com.robosoftin.lorem_food_app.entity.Restaurant.MenuItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class CartItemKey implements Serializable {
    @ManyToOne(targetEntity = MyCart.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    private MyCart myCart;
    @ManyToOne(targetEntity = MenuItem.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_item_id", referencedColumnName = "id")
    private MenuItem menuItem;
}
