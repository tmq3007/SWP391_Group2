package com.shoppingapp.shoppingapp.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transId;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @OneToOne
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false )
    private Shop shop;

    private LocalDateTime date = LocalDateTime.now();
}
