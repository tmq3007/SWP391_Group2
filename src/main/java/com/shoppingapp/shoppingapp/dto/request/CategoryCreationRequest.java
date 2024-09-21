package com.shoppingapp.shoppingapp.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCreationRequest {
    private String categoryName;
    private String description;
    private String picture;
    private Boolean isActive;
}
