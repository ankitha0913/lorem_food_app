package com.robosoftin.lorem_food_app.security;
import com.robosoftin.lorem_food_app.exception.BearerTokenNotFoundException;
import com.robosoftin.lorem_food_app.exception.UnauthorizedException;
import com.robosoftin.lorem_food_app.service.JwtService;
import com.robosoftin.lorem_food_app.utility.JwtUtility;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if(authorization!=null && authorization.startsWith("Bearer ")){
            token = authorization.substring(7);
            try
            {
                username = jwtUtility.getUsernameFromToken(token);
            }
            catch (ExpiredJwtException exception)
            {
                throw new UnauthorizedException("Token Expired!");
            }
        }
        else
            throw new BearerTokenNotFoundException("Couldn't find bearer token");
        if (username!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails= jwtService.loadUserByUsername(username);
            if (jwtUtility.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            else
                throw new UnauthorizedException("Invalid Token");
            filterChain.doFilter(request,response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.startsWith("/api/user/") || path.startsWith("/api/restaurant/");
    }
}
