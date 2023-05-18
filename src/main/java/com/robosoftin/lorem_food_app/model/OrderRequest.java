package com.robosoftin.lorem_food_app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.robosoftin.lorem_food_app.entity.Order.Address;
import com.robosoftin.lorem_food_app.enums.DeliveryType;
import com.robosoftin.lorem_food_app.enums.PaymentMode;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderRequest {
    private String emailId;
    private long cartId;
    private String date;
    private String time;
    private Address address;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cookingInstruction;
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;
    private String contactName;
    private String mobileNo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String deliveryInstruction;
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;
    private double itemCost;
}
