package com.robosoftin.lorem_food_app.entity.Cart;

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
@Table(name = "cart_item")
public class CartItem {
    @EmbeddedId
    private CartItemKey cartItemKey;
    private int quantity;
}
