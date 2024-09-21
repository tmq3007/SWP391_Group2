package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.models.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User addUser(User user);

    User updateUser(User User, Long id);

    String deleteUser(Long id);

    User getMyInfo();
}
