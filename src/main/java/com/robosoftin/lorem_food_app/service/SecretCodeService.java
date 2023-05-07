package com.robosoftin.lorem_food_app.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.robosoftin.lorem_food_app.exception.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class SecretCodeService {
    private static final Integer EXPIRE_MINS = 15;
    private LoadingCache<String,String> secretCodeCache;

    public SecretCodeService(){
        super();
        secretCodeCache= CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return "EXPIRED";
                    }
                });
    }

    public String generateSecretCode(String emailId){
        String code=UUID.randomUUID().toString();
        secretCodeCache.put(emailId,code);
        return code;
    }

    public boolean validateOtp(String emailId,String code) throws ExecutionException {
        String cacheCode=secretCodeCache.get(emailId);
        if(cacheCode=="EXPIRED")
        {
            secretCodeCache.invalidate(emailId);
            throw new UnauthorizedException("Secret code has been expired or was not found for emailId - "+emailId);
        }
        else if(!cacheCode.equals(code))
            throw new UnauthorizedException("Invalid Secret Code");
        else {
            secretCodeCache.invalidate(emailId);
            return true;
        }
    }
}
