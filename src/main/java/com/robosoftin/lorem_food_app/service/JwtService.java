package com.robosoftin.lorem_food_app.service;

import com.robosoftin.lorem_food_app.dao.UserRepository;
import com.robosoftin.lorem_food_app.entity.Auth.UserInfo;
import com.robosoftin.lorem_food_app.exception.UnauthorizedException;
import com.robosoftin.lorem_food_app.exception.NotFoundException;
import com.robosoftin.lorem_food_app.model.JwtRequest;
import com.robosoftin.lorem_food_app.model.JwtResponse;
import com.robosoftin.lorem_food_app.utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtility jwtUtility;
    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        UserInfo user=userRepository.findByEmailId(emailId);
        if (user==null)
            throw new NotFoundException("User Not Found - "+emailId);
        return new User(user.getEmailId(),user.getPassword(),new ArrayList<>());
    }

    public JwtResponse createUser(UserInfo user){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            final UserDetails userDetails =loadUserByUsername(user.getEmailId());
            final String token = jwtUtility.generateToken(userDetails);
            final String refreshToken = refreshTokenService.createRefreshToken(user.getId()).getToken();
            return new JwtResponse(token,refreshToken,"User Registration Successful",getUserData(user));
    }

    public JwtResponse loginUser(JwtRequest jwtRequest){
        final UserDetails userDetails =loadUserByUsername(jwtRequest.getEmailId());
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    jwtRequest.getEmailId(),jwtRequest.getPassword()
            ));
        }
        catch (BadCredentialsException e){
            throw new UnauthorizedException("Invalid Credentials");
        }
        final String token = jwtUtility.generateToken(userDetails);
        UserInfo user=userRepository.findByEmailId(userDetails.getUsername());
        final String refreshToken = refreshTokenService.createRefreshToken(user.getId()).getToken();
        return new JwtResponse(token,refreshToken,"User LoggedIn Successfully",getUserData(user));
    }

    public UserInfo updateUserPassword(String emailId,String newPassword){
        final UserDetails userDetails =loadUserByUsername(emailId);
        UserInfo user = userRepository.findByEmailId(userDetails.getUsername());
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return user;
    }

    private Map<String,String> getUserData(UserInfo user){
        final Map<String,String> userInformation = new HashMap<>();
        userInformation.put("emailId",user.getEmailId());
        userInformation.put("firstName",user.getFirstName());
        userInformation.put("lastName",user.getLastName());
        if (user.getMobileNo()!=null)
            userInformation.put("mobileNo",user.getMobileNo());
        return userInformation;
    }
}
