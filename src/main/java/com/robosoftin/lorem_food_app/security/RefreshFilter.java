package com.robosoftin.lorem_food_app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.robosoftin.lorem_food_app.exception.*;
import com.robosoftin.lorem_food_app.service.BlacklistTokenService;
import com.robosoftin.lorem_food_app.service.JwtService;
import com.robosoftin.lorem_food_app.service.RefreshTokenService;
import com.robosoftin.lorem_food_app.utility.JwtUtility;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private BlacklistTokenService blacklistTokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtUser=null;
        String username = null;
        String token = null;
        String refreshToken = request.getHeader("refresh-token");
        String authorization = request.getHeader("Authorization");
        AuthExceptionHandler exceptionHandler = new AuthExceptionHandler();
        try{
            if(refreshToken!=null)
            {
                if(authorization!=null && authorization.startsWith("Bearer ")){
                    token = authorization.substring(7);
                    try{
                        jwtUser = jwtUtility.getUsernameFromToken(token);
                        username = refreshTokenService.getUsernameFromToken(refreshToken);
                        if (jwtUser.equals(username))
                            blacklistTokenService.addToBlacklist(token);
                        else
                            throw new Exception("Tokens Mismatch");
                    }catch (ExpiredJwtException exception)
                    {

                    }
                }
                else
                    throw new BearerTokenNotFoundException("Couldn't find bearer token");
            }
            else
                throw new BearerTokenNotFoundException("Couldn't find refresh token");
            if(username!=null)
            {
                if (refreshTokenService.validateToken(refreshToken,username,token))
                    request.setAttribute("emailId",username);
                else
                    throw new UnauthorizedException("Refresh Token Expired!");
                filterChain.doFilter(request,response);
            }
            else
                throw new UserNotFoundException("No match found for refresh token");
        }
        catch (UnauthorizedException exception)
        {
            exceptionHandler.handleFilterException(response,HttpStatus.UNAUTHORIZED,exception.getMessage());
        }
        catch (UserNotFoundException exception)
        {
            exceptionHandler.handleFilterException(response,HttpStatus.NOT_FOUND,exception.getMessage());
        }
        catch (BearerTokenNotFoundException exception)
        {
            exceptionHandler.handleFilterException(response,HttpStatus.FORBIDDEN,exception.getMessage());
        }
        catch (Exception exception)
        {
            exceptionHandler.handleFilterException(response,HttpStatus.BAD_REQUEST,exception.getMessage());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return !path.startsWith("/api/user/refresh");
    }
}
