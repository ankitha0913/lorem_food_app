package com.robosoftin.lorem_food_app.entity.Order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.robosoftin.lorem_food_app.entity.Auth.UserInfo;
import com.robosoftin.lorem_food_app.entity.Restaurant.Restaurant;
import com.robosoftin.lorem_food_app.enums.DeliveryType;
import com.robosoftin.lorem_food_app.enums.OrderStatus;
import com.robosoftin.lorem_food_app.enums.PaymentMode;
import com.robosoftin.lorem_food_app.enums.RestaurantStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "my_order")
public class Order {
    @Id
    private Long id;
    @ManyToOne(targetEntity = UserInfo.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo user;
    @ManyToOne(targetEntity = Restaurant.class)
    @JoinColumn(name = "rest_id", referencedColumnName = "id")
    private Restaurant restaurant;
    private String date;
    private String time;
    @ManyToOne(targetEntity = Address.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
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
    private double extraCharge;
    private double discount;
    private double totalAmount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
