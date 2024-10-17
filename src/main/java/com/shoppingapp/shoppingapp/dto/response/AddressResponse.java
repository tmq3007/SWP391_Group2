package com.shoppingapp.shoppingapp.dto.response;

import com.shoppingapp.shoppingapp.models.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long addressID;
    private String city;
    private String district;
    private String subDistrict;
    private String street;
    private User user;
}
