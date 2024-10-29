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
@Data
@Builder
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long shopId;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private User user;
    private String shopName;
    String address;
    String city;
    String district;
    String subdistrict;
    String description;
    String phone;
    String logo;
    String cover;

    // Constructor with ID
    public Shop(Long shopId) {
        this.shopId = shopId;
    }

    // Default constructor
    public Shop() {
    }
}
