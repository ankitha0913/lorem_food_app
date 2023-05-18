package com.robosoftin.lorem_food_app.dao;

import com.robosoftin.lorem_food_app.entity.Order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

}
