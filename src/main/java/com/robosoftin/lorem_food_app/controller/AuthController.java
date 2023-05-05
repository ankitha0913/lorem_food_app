package com.robosoftin.lorem_food_app.controller;

import com.robosoftin.lorem_food_app.entity.UserInfo;
import com.robosoftin.lorem_food_app.model.JwtResponse;
import com.robosoftin.lorem_food_app.model.JwtRequest;
import com.robosoftin.lorem_food_app.model.StatusResponse;
import com.robosoftin.lorem_food_app.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserInfo userInfo) {
        JwtResponse jwtResponse = jwtService.createUser(userInfo);
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) {
        JwtResponse jwtResponse = jwtService.loginUser(jwtRequest);
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }

    @GetMapping("/email/{emailId}")
    public ResponseEntity<StatusResponse> verifyEmail(@PathVariable String emailId) {
        UserDetails user = jwtService.loadUserByUsername(emailId);
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponse(true, HttpStatus.OK.value(), "User with this emailId exists"));
    }

}
