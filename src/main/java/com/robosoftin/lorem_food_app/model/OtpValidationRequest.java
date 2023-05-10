package com.robosoftin.lorem_food_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OtpValidationRequest {
    private String emailId;
    private int otp;
    private boolean isRegister;
}
