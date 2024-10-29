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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long addressID;

    private String city;
    private String district;
    private String subDistrict;
    private String street;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    @JsonIgnore
    private User user;
}
