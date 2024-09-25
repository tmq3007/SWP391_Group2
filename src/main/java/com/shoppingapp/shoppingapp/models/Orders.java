package com.shoppingapp.shoppingapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


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
    User user;

    @OneToOne
    Payment payment;

    LocalDate paymentDate;

    LocalDate orderDate;

    Boolean isPaid;
}
