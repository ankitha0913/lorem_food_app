package com.robosoftin.lorem_food_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "user")
public class UserInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String emailId;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String password;

    public UserInfo(String emailId, String firstName, String lastName, String mobileNo, String password) {
        this.emailId = emailId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNo = mobileNo;
        this.password = password;
    }

    public UserInfo(String emailId, String firstName, String lastName, String password) {
        this.emailId = emailId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
}
