package com.robosoftin.lorem_food_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdatePasswordRequest {
    private String emailId;
    private String newPassword;
    private String secretCode;
}
