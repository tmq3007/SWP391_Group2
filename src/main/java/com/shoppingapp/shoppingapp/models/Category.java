package com.shoppingapp.shoppingapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long categoryId;
     String categoryName;
     String description;
     String picture;
     Boolean isActive;

    public Category(Long categoryId) {
        this.categoryId = categoryId;
    }

    // Default constructor
    public Category() {
    }
}
