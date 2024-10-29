package com.shoppingapp.shoppingapp.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItems> orderItemsList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
