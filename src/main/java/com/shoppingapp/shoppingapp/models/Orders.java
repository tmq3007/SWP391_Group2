package com.shoppingapp.shoppingapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

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


    @OneToMany
    private List<OrderItems> orderItemsList;

    @ManyToOne
    private User user;
}
