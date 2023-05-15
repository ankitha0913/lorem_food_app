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
@Table(name = "my_cart")
public class MyCart {
    @Column(name = "cart_id",unique = true)
    private Long cartId;
    @EmbeddedId
    private MyCartKey myCartKey;
}
