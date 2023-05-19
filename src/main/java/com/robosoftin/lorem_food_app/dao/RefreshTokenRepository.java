package com.robosoftin.lorem_food_app.dao;

import com.robosoftin.lorem_food_app.entity.Auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    RefreshToken findByToken(String token);

    @Modifying
    @Query("DELETE RefreshToken r WHERE r.user.id=?1")
    void deleteByUserId(int userId);
}
