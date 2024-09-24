package com.shoppingapp.shoppingapp.dto.response;

import jakarta.persistence.Entity;
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
public class CategoryResponse {
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long categoryId;
    String categoryName;
    String description;
    String picture;
    Boolean isActive;
}