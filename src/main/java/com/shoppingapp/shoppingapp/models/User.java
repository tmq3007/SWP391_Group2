package com.shoppingapp.shoppingapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

     String firstName;
     String lastName;

     String email;

     String username;
     String password;

     String phone;

     boolean isActive;

     @ManyToMany
    Set<Role> roles;

}
