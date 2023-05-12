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
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id",columnDefinition = "INT(20) NOT NULL UNIQUE KEY")
    private int cart_id;
    @EmbeddedId
    private MyCartKey myCartKey;

    public MyCart(MyCartKey myCartKey) {
        this.myCartKey = myCartKey;
    }
}
