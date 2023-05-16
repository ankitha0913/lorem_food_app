package com.robosoftin.lorem_food_app.service;
import com.robosoftin.lorem_food_app.dao.BlacklistTokenRepository;
import com.robosoftin.lorem_food_app.entity.Auth.BlacklistToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
public class BlacklistTokenService {
    @Autowired
    private BlacklistTokenRepository blacklistTokenRepository;

    public void addToBlacklist(String token)
    {
        blacklistTokenRepository.save(new BlacklistToken(token));
    }

    public boolean isBlacklisted(String token)
    {
        try {
            blacklistTokenRepository.findById(token).get();
            return true;
        }catch (NoSuchElementException exception){
            return false;
        }
    }
}
