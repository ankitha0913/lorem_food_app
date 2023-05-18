package com.robosoftin.lorem_food_app.dao;

import com.robosoftin.lorem_food_app.entity.Cart.MyCart;
import com.robosoftin.lorem_food_app.entity.Cart.MyCartKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<MyCart,MyCartKey> {
    void deleteByCartId(long cartId);
    MyCart findByCartId(long cartId);
    @Query("SELECT c FROM MyCart c WHERE c.myCartKey.userInfo.id=?1")
    List<MyCart> findByUserId(int userId);

    @Query("SELECT c FROM MyCart c WHERE c.myCartKey.restaurant.id=?1 AND c.myCartKey.userInfo.id=?2")
    MyCart findByRestIdAndUserId(int restId,int userId);
}
