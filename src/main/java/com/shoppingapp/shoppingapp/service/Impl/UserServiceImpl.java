package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.enums.Role;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.exceptions.ResourceNotFoundException;
import com.shoppingapp.shoppingapp.models.User;

import com.shoppingapp.shoppingapp.repository.UserRepository;
import com.shoppingapp.shoppingapp.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {


     UserRepository userRepository;


     PasswordEncoder passwordEncoder;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {

        log.info("In method get all users");
        return userRepository.findAll();
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public User getUserById(Long id) {

        log.info("In method get user by id");

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_EXISTED));
    }

    @Override
    public User addUser(User user) {

        var isEmailExist = userRepository.findByEmail(user.getEmail());
        if(isEmailExist.isPresent()){
            throw new ResourceNotFoundException(ErrorCode.EMAIL_EXISTED);
        }

        var isUsernameExist = userRepository.findByUsername(user.getUsername());
        if(isUsernameExist.isPresent()){
            throw new ResourceNotFoundException(ErrorCode.USER_EXISTED);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.CUSTOMER.name());

        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User updatedUser, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_EXISTED));


        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setActive(updatedUser.isActive());

        return userRepository.save(user);
    }

    @Override
    public String deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_EXISTED));

        userRepository.delete(user);

        return "User deleted successfully";
    }

    @Override
    public User getMyInfo()  {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        return userRepository.findByUsername(name)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_EXISTED));
    }
}
