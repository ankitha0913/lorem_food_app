package com.robosoftin.lorem_food_app.controller;

import com.robosoftin.lorem_food_app.model.OtpValidationRequest;
import com.robosoftin.lorem_food_app.model.StatusResponse;
import com.robosoftin.lorem_food_app.service.EmailService;
import com.robosoftin.lorem_food_app.service.OtpService;
import com.robosoftin.lorem_food_app.service.SecretCodeService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/user")
public class OtpController {
    @Autowired
     public OtpService otpService;

    @Autowired
    public EmailService emailService;

    @Autowired
    private SecretCodeService secretCodeService;

    @GetMapping("/generate-otp/{emailId}")
    public ResponseEntity<StatusResponse> generateOtp(@PathVariable String emailId) throws MessagingException {
        int otp=otpService.generateOtp(emailId);
        emailService.sendOtpMessage(emailId,"OTP Verification",otp+" is the OTP to verify your emailId.");
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponse(HttpStatus.OK.value(), "OTP sent"));
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<StatusResponse> validateOtp(@RequestBody OtpValidationRequest request) throws ExecutionException {
        otpService.validateOtp(request.getEmailId(),request.getOtp());
        if(request.isRegister())
            return ResponseEntity.status(HttpStatus.OK).body(new StatusResponse( HttpStatus.OK.value(), "OTP valid"));
        String secretCode = secretCodeService.generateSecretCode(request.getEmailId());
        return ResponseEntity.status(HttpStatus.OK).body(new StatusResponse(HttpStatus.OK.value(), "OTP valid",secretCode));
    }
}
