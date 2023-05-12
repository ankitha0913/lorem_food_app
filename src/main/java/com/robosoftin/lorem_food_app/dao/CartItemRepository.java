package com.robosoftin.lorem_food_app.dao;

import com.robosoftin.lorem_food_app.entity.Cart.CartItem;
import com.robosoftin.lorem_food_app.entity.Cart.CartItemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemKey> {
}
