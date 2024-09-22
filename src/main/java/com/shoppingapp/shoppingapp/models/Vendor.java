package com.shoppingapp.shoppingapp.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vendor extends User {
    @OneToMany
    @JoinColumn(name = "vendor_id", nullable = false)
    Set<Shop> shop;
}
