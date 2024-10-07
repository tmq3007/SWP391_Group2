package com.shoppingapp.shoppingapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long  id;

    @Column(nullable = false)
    String reviewText;

    @Column(nullable = false)
    Double rating;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false) // Sử dụng @JoinColumn thay vì @Column
    Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Sử dụng @JoinColumn
    User user;

    @Column(nullable = false)
    LocalDateTime createAt = LocalDateTime.now();
}
