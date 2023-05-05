package com.robosoftin.lorem_food_app.dao;

import com.robosoftin.lorem_food_app.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfo,Integer> {
    UserInfo findByEmailId(String emailId);
}
