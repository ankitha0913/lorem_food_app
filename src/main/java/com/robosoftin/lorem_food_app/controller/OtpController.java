package com.robosoftin.lorem_food_app.controller;

import com.robosoftin.lorem_food_app.model.StatusResponse;
import com.robosoftin.lorem_food_app.service.EmailService;
import com.robosoftin.lorem_food_app.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class OtpController {
    @Autowired
    public OtpService otpService;

    @Autowired
    public EmailService emailService;

    @GetMapping("/generateOtp/{emailId}")
    public ResponseEntity<StatusResponse> generateOtp(@PathVariable String emailId){
        int otp=otpService.generateOtp(emailId);
        emailService.sendOtpMessage(emailId,"OTP Verification",otp+" is the OTP to verify your emailId.");
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponse(true, HttpStatus.OK.value(), "OTP sent"));
    }
}
