package com.robosoftin.lorem_food_app.entity.Order;

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
@Table(name = "order_item")
public class OrderItem {
    @EmbeddedId
    private OrderItemKey orderItemKey;
    private int quantity;
}
