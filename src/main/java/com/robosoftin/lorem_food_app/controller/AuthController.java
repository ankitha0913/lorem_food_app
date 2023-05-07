package com.robosoftin.lorem_food_app.controller;

import com.robosoftin.lorem_food_app.entity.UserInfo;
import com.robosoftin.lorem_food_app.model.JwtResponse;
import com.robosoftin.lorem_food_app.model.JwtRequest;
import com.robosoftin.lorem_food_app.model.StatusResponse;
import com.robosoftin.lorem_food_app.model.UpdatePasswordRequest;
import com.robosoftin.lorem_food_app.service.JwtService;
import com.robosoftin.lorem_food_app.service.SecretCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private SecretCodeService secretCodeService;

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
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponse(true, HttpStatus.OK.value(), "User with emailId - "+user.getUsername()+" exists"));
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest request) throws ExecutionException {
        boolean isValid = secretCodeService.validateOtp(request.getEmailId(), request.getSecretCode());
        final UserInfo userInfo = jwtService.updateUserPassword(request.getEmailId(), request.getNewPassword());
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponse(isValid,HttpStatus.OK.value(),"Password updated for user "+userInfo.getEmailId()));
    }

}
