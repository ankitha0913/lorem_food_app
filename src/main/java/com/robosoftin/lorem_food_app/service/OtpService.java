package com.robosoftin.lorem_food_app.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {
    private static final Integer EXPIRE_MINS = 4;
    private LoadingCache<String,Integer> otpCache;

    public OtpService(){
        super();
        otpCache= CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) throws Exception {
                        return 0;
                    }
                });
    }

    public int generateOtp(String key){
        Random random=new Random();
        int otp=random.nextInt(1000,10000);
        otpCache.put(key,otp);
        return otp;
    }

    public int getOtp(String key){
        try {
            return otpCache.get(key);
        } catch (ExecutionException e) {
            return 0;
        }
    }

    public void clearOTP(String key){
        otpCache.invalidate(key);
    }
}
