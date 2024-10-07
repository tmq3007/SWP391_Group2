package com.shoppingapp.shoppingapp.dto.request;

import com.shoppingapp.shoppingapp.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddressCreationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long addressID;

    private String city;
    private String district;
    private String subDistrict;
    private String street;
    private Long user;
}

