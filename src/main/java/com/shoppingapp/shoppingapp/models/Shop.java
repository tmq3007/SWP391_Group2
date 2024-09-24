package com.shoppingapp.shoppingapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PUBLIC)
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long shopID;
     String shopName;
     String address;
     String city;
     String state;
     String country;
     String phone;

}
