package com.robosoftin.lorem_food_app.security;

import com.robosoftin.lorem_food_app.exception.BearerTokenNotFoundException;
import com.robosoftin.lorem_food_app.exception.UnauthorizedException;
import com.robosoftin.lorem_food_app.exception.UserNotFoundException;
import com.robosoftin.lorem_food_app.service.JwtService;
import com.robosoftin.lorem_food_app.service.RefreshTokenService;
import com.robosoftin.lorem_food_app.utility.JwtUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RefreshFilter extends OncePerRequestFilter {
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private JwtUtility jwtUtility;
    @Autowired
    private JwtService jwtService;
    public static String emailId;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = null;
        String refreshToken = request.getHeader("refresh-token");
        if(refreshToken!=null){
            username = refreshTokenService.getUsernameFromToken(refreshToken);
        }
        else
            throw new BearerTokenNotFoundException("Couldn't find refresh token");
        if(username!=null)
        {
            if (refreshTokenService.validateToken(refreshToken,username))
            {
                emailId=username;
            }else
                throw new UnauthorizedException("Refresh Token Expired!");
            filterChain.doFilter(request,response);
        }
        else
            throw new UserNotFoundException("No match found for refresh token");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return !path.startsWith("/api/user/refresh");
    }
}
