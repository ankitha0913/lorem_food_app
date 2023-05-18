package com.robosoftin.lorem_food_app.entity.Order;

import com.robosoftin.lorem_food_app.enums.AddressType;
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
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String location;
    private boolean isPrimary;
    @Enumerated(EnumType.STRING)
    private AddressType type;

    public Address(String location, boolean isPrimary, AddressType type) {
        this.location = location;
        this.isPrimary = isPrimary;
        this.type = type;
    }
}
