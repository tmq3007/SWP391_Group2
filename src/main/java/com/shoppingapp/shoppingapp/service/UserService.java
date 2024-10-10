package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.UserCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.UserUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.UserResponse;
import com.shoppingapp.shoppingapp.models.User;

import java.util.List;

public interface UserService {

   UserResponse createUser(UserCreationRequest userCreationRequest);

   UserResponse getMyInfo();

   UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest);

   void deleteUser(Long id);

   List<UserResponse> getAll();

   List<UserResponse> getVendors();

   List<UserResponse> getCustomers();

   UserResponse getUserById(Long id);

   int getTotalVendors();

   void banUser(Long id);

   void unbanUser(Long id);

}
