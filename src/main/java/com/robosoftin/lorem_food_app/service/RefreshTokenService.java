package com.robosoftin.lorem_food_app.service;

import com.robosoftin.lorem_food_app.dao.RefreshTokenRepository;
import com.robosoftin.lorem_food_app.dao.UserRepository;
import com.robosoftin.lorem_food_app.entity.Auth.RefreshToken;
import com.robosoftin.lorem_food_app.entity.Auth.UserInfo;
import com.robosoftin.lorem_food_app.exception.UnauthorizedException;
import com.robosoftin.lorem_food_app.model.JwtResponse;
import com.robosoftin.lorem_food_app.model.StatusResponse;
import com.robosoftin.lorem_food_app.utility.JwtUtility;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.UUID;

import static java.time.Instant.now;

@Service
public class RefreshTokenService {
    //3 days
    static final long REFRESH_TOKEN_VALIDITY = 60 * 60 * 24 * 3 * 1000;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private BlacklistTokenService blacklistTokenService;

    public RefreshToken createRefreshToken(int userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(now().plusMillis(REFRESH_TOKEN_VALIDITY));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public String getUsernameFromToken(String token)
    {
        RefreshToken refreshToken=refreshTokenRepository.findByToken(token);
        if (refreshToken==null)
            throw new UnauthorizedException("Invalid Refresh Token");
        return refreshToken.getUser().getEmailId();
    }

    public boolean validateToken(String token,String emailId,String jwt)
    {
        RefreshToken refreshToken=refreshTokenRepository.findByToken(token);
        if (emailId.equals(refreshToken.getUser().getEmailId()))
        {
            if (now().isAfter(refreshToken.getExpiryDate()))
            {
                blacklistTokenService.addToBlacklist(jwt);
                refreshTokenRepository.deleteById(refreshToken.getId());
                return false;
            }
            else
                return true;
        }
        else
            return false;
    }

    public StatusResponse generateNewToken(UserDetails userDetails, HttpServletResponse response){
        final String token = jwtUtility.generateToken(userDetails);
        response.setHeader("Authorization","Bearer "+token);
        return new StatusResponse(HttpStatus.OK.value(),"New Jwt token generated");
    }

    @Transactional
    public StatusResponse deleteToken(String emailId)
    {
        UserInfo userInfo=userRepository.findByEmailId(emailId);
        refreshTokenRepository.deleteByUserId(userInfo.getId());
        return new StatusResponse(HttpStatus.OK.value(),"User logged out successfully");
    }
}
