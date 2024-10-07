package com.shoppingapp.shoppingapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderId;

    @ManyToOne
    //One user can have many order
    User user;

    @OneToMany
    //An order can have many items inside it
    List<OrderItems> orderItems = new ArrayList<>();

    @OneToOne
    //One order can only pay one
    Payment payment;

    @ManyToOne
    //Many order can have same address
    //One order can only have one address
    Address address;

    LocalDate paymentDate;

    LocalDate orderDate;

    Boolean isPaid;
}
