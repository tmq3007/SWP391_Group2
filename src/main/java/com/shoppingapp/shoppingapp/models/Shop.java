package com.shoppingapp.shoppingapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long shopId;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @OneToMany
    @JsonIgnore

    private Set<Product> products;

    @ManyToMany

    @JsonIgnore
    private Set<Orders> order;
    private String shopName;
    private String address;
    private String city;
    private String state;
    private String country;
    private String phone;
    private String description;
    private String logo;
    private String cover;

}
