package com.robosoftin.lorem_food_app.dao;

import com.robosoftin.lorem_food_app.entity.Auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    RefreshToken findByToken(String token);

}
