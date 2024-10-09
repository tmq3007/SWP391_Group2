package com.shoppingapp.shoppingapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

     Boolean isActive;

     @ManyToMany
    Set<Role> roles;

     @OneToMany
     @JsonIgnore
    Set<Address> addresses;


}
