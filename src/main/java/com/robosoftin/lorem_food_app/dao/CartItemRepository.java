package com.robosoftin.lorem_food_app.dao;

import com.robosoftin.lorem_food_app.entity.Cart.CartItem;
import com.robosoftin.lorem_food_app.entity.Cart.CartItemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemKey> {
    @Modifying
    @Query("DELETE CartItem c WHERE c.cartItemKey.myCart.cartId=?1")
    void deleteByCartId(long cartId);

    @Modifying
    @Query("DELETE CartItem c WHERE c.cartItemKey.myCart.cartId=?1 AND c.cartItemKey.menuItem.id=?2")
    void deleteByCartIdAndMenuItemId(long cartId,int menuItemId);
}
