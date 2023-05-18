package com.robosoftin.lorem_food_app.dao;

import com.robosoftin.lorem_food_app.entity.Order.OrderItem;
import com.robosoftin.lorem_food_app.entity.Order.OrderItemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemKey> {
}
