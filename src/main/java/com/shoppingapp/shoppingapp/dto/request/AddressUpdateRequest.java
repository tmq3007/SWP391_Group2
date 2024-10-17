package com.shoppingapp.shoppingapp.dto.request;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddressUpdateRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long addressID;
    private String city;
    private String district;
    private String subDistrict;
    private String street;
}
