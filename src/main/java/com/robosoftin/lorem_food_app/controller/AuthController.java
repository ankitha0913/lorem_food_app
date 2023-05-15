package com.robosoftin.lorem_food_app.controller;

import com.robosoftin.lorem_food_app.entity.Auth.UserInfo;
import com.robosoftin.lorem_food_app.model.JwtResponse;
import com.robosoftin.lorem_food_app.model.JwtRequest;
import com.robosoftin.lorem_food_app.model.StatusResponse;
import com.robosoftin.lorem_food_app.model.UpdatePasswordRequest;
import com.robosoftin.lorem_food_app.security.RefreshFilter;
import com.robosoftin.lorem_food_app.service.JwtService;
import com.robosoftin.lorem_food_app.service.RefreshTokenService;
import com.robosoftin.lorem_food_app.service.SecretCodeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH},
        origins = {"http://localhost:8080","https://main-sphere-386011.uc.r.appspot.com"})
public class AuthController {

        @Autowired
        private JwtService jwtService;
        @Autowired
        private SecretCodeService secretCodeService;
        @Autowired
        private RefreshTokenService refreshTokenService;

        @PostMapping("/register")
        public ResponseEntity<JwtResponse> register(@RequestBody UserInfo userInfo) {
        JwtResponse jwtResponse = jwtService.createUser(userInfo);
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        JwtResponse jwtResponse = jwtService.loginUser(jwtRequest);
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }

    @GetMapping("/email/{emailId}")
    public ResponseEntity<StatusResponse> verifyEmail(@PathVariable String emailId) {
        UserDetails user = jwtService.loadUserByUsername(emailId);
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponse( HttpStatus.OK.value(), "User with emailId - "+user.getUsername()+" exists"));
    }

    @PutMapping("/update-password")
    public ResponseEntity<StatusResponse> updatePassword(@RequestBody UpdatePasswordRequest request) throws ExecutionException {
        secretCodeService.validateOtp(request.getEmailId(), request.getSecretCode());
        final UserInfo userInfo = jwtService.updateUserPassword(request.getEmailId(), request.getNewPassword());
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponse(HttpStatus.OK.value(),"Password updated for user "+userInfo.getEmailId()));
    }

    @GetMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(HttpServletRequest httpServletRequest) {
        String emailId=(String) httpServletRequest.getAttribute(RefreshFilter.emailId);
        JwtResponse jwtResponse=refreshTokenService.generateNewToken(jwtService.loadUserByUsername(emailId));
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }

}
