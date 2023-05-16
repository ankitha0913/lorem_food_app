package com.robosoftin.lorem_food_app.dao;

import com.robosoftin.lorem_food_app.entity.Auth.BlacklistToken;
import com.robosoftin.lorem_food_app.entity.Auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistTokenRepository extends JpaRepository<BlacklistToken,String> {

}
