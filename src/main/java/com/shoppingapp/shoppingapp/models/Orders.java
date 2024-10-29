package com.shoppingapp.shoppingapp.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long paymentId;

    private LocalDate paymentDate;

    private LocalDate orderDate;

    private Boolean isPaid;

    private Double total;

    private Double finalTotal;

    private String note;

    private String address;

    private String phone;

    @ManyToOne
    private User user;

}
