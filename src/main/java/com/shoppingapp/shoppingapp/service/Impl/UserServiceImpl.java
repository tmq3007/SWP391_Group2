package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.exceptions.ResourceNotFoundException;
import com.shoppingapp.shoppingapp.models.User;

import com.shoppingapp.shoppingapp.repository.UserRepository;
import com.shoppingapp.shoppingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with the id" + id));
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User User, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with the id" + id));

        user.setFirstName(User.getFirstName());
        user.setLastName(User.getLastName());
        user.setEmail(User.getEmail());
        user.setPhone(User.getPhone());
        user.setUsername(User.getUsername());
        user.setPassword(User.getPassword());
        user.setActive(User.isActive());

        return userRepository.save(User);
    }

    @Override
    public String deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with the id" + id));

        userRepository.delete(user);

        return "User deleted successfully";
    }
}
