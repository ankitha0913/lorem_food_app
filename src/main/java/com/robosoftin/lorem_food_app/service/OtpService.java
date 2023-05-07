package com.robosoftin.lorem_food_app.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.robosoftin.lorem_food_app.exception.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {
    private static final Integer EXPIRE_MINS = 2;
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

    public boolean validateOtp(String emailId,int otp) throws ExecutionException{
        int cacheOtp=otpCache.get(emailId);
        if(cacheOtp==0)
        {
            otpCache.invalidate(emailId);
            throw new UnauthorizedException("OTP has been Expired or was not generated for emailId -"+emailId);
        }
        else if(cacheOtp!=otp)
            throw new UnauthorizedException("Invalid OTP");
        else {
            otpCache.invalidate(emailId);
            return true;
        }
    }
}
