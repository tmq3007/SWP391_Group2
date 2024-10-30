package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.UserCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.UserUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.UserResponse;
import com.shoppingapp.shoppingapp.dto.response.VendorResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.mapper.UserMapper;
import com.shoppingapp.shoppingapp.models.Role;
import com.shoppingapp.shoppingapp.models.User;
import com.shoppingapp.shoppingapp.repository.RoleRepository;
import com.shoppingapp.shoppingapp.repository.UserRepository;
import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.nio.file.AccessDeniedException;
import java.util.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserMapper userMapper;

    private UserUpdateRequest updateRequest;

    private UserCreationRequest request;
    private UserResponse userResponse;

    private UserResponse updatedUserResponse;
    private User user;

    private User oldUser;

    private Role role;

    private User vendorUser1;
    private User vendorUser2;
    private User customerUser;

    private UserResponse vendorResponse1;
    private UserResponse vendorResponse2;

    private UserResponse customerResponse1;

    @BeforeEach
    void initData() {

        request = UserCreationRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("aro177@gmail.com")
                .username("johndoe")
                .password("password")
                .phone("08012345678")
                .roles(Set.of("CUSTOMER"))
                .build();

        userResponse = UserResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("aro177@gmail.com")
                .username("johndoe")
                .phone("08012345678")
                .roles(Set.of(Role.builder()
                        .name("CUSTOMER")
                        .description("Customer Role")
                        .build()))
                .build();

        user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("aro177@gmail.com")
                .username("johndoe")
                .phone("08012345678")
                .roles(Set.of(Role.builder()
                        .name("CUSTOMER")
                        .description("Customer Role")
                        .build()))
                .build();
        // Prepare the UserUpdateRequest
        updateRequest = UserUpdateRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("aro177@gmail.com")
                .username("johndoe")
                .password("newPassword")
                .isActive(true)
                .Roles(Set.of("CUSTOMER"))
                .build();

        // Create a role instance
        role = Role.builder()
                .name("CUSTOMER")
                .description("Customer Role")
                .permissions(Set.of()) // Assuming no permissions for simplicity
                .build();

        // Prepare the User instance
        oldUser = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("aro177@gmail.com")
                .username("johndoe")
                .phone("08012345678")
                .password("oldEncodedPassword")
                .roles(Set.of(role))
                .build();

        // Prepare the UserResponse instance
        updatedUserResponse = UserResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("aro177@gmail.com")
                .username("johndoe")
                .phone("08012345678")
                .build();

        Role vendorRole = Role.builder().name("VENDOR").description("Vendor Role").build();
        Role customerRole = Role.builder().name("CUSTOMER").description("Customer Role").build();

        vendorUser1 = User.builder()
                .id(1L)
                .username("vendor1")
                .roles(Set.of(vendorRole))
                .build();

        vendorUser2 = User.builder()
                .id(2L)
                .username("vendor2")
                .roles(Set.of(vendorRole))
                .build();


        customerUser = User.builder()
                .id(3L)
                .username("customer1")
                .roles(Set.of(customerRole))
                .build();


        vendorResponse1 = UserResponse.builder()
                .id(1L)
                .username("vendor1")
                .build();

        vendorResponse2 = UserResponse.builder()
                .id(2L)
                .username("vendor2")
                .build();

        customerResponse1 = UserResponse.builder()
                .id(3L)
                .username("customer1")
                .build();

    }


    @Test
    void createUser_validRequest_success() {
        //GIVEN
        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(ArgumentMatchers.anyString())).thenReturn(false);
        Mockito.when(userMapper.toUser(ArgumentMatchers.any())).thenReturn(user);
        Mockito.when(userMapper.toUserResponse(ArgumentMatchers.any())).thenReturn(userResponse);
        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);

        //WHEN
        var response = userService.createUser(request);

        //THEN
        Assertions.assertThat(response.getId()).isEqualTo(1L);
        Assertions.assertThat(response.getUsername()).isEqualTo("johndoe");
        Assertions.assertThat(response.getEmail()).isEqualTo("aro177@gmail.com");
        Assertions.assertThat(response.getRoles()).isEqualTo(Set.of(Role.builder()
                .name("CUSTOMER")
                .description("Customer Role")
                .build()));
    }

    @Test
    void createUser_userExisted_fail() {
        // GIVEN
        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString())).thenReturn(true);

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.createUser(request));

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1000);
    }

    @Test
    void createUser_emailExisted_fail() {
        // GIVEN
        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(ArgumentMatchers.anyString())).thenReturn(true);


        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.createUser(request));

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1008);
    }

    @Test
    @WithMockUser(username = "johndoe")
    void getMyInfo_valid_success() {
        // GIVEN
        Mockito.when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userMapper.toUserResponse(ArgumentMatchers.any())).thenReturn(userResponse);

        // WHEN
        var response = userService.getMyInfo();

        // THEN
        Assertions.assertThat(response.getUsername()).isEqualTo("johndoe");
        Assertions.assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    @WithMockUser(username = "johndoe")
    void getMyInfo_userNotFound_error() {
        Mockito.when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.ofNullable(null));

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.getMyInfo());

        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1005);
    }

    @Test
    @WithMockUser(username = "johndoe")
    void updateUser_validRequest_success() {
        // GIVEN
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(oldUser));
        Mockito.when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        Mockito.when(roleRepository.findAllById(updateRequest.getRoles())).thenReturn(new ArrayList<>(Set.of(role)));// Match by name
        Mockito.when(userRepository.save(oldUser)).thenReturn(oldUser);
        Mockito.when(userMapper.toUserResponse(oldUser)).thenReturn(updatedUserResponse);

        // WHEN
        var response = userService.updateUser(1L, updateRequest);

        // THEN
        Assertions.assertThat(response.getId()).isEqualTo(1L);
        Assertions.assertThat(response.getUsername()).isEqualTo("johndoe");
        Assertions.assertThat(response.getEmail()).isEqualTo("aro177@gmail.com");

        // Verify password has been updated
        Mockito.verify(passwordEncoder).encode("newPassword");
        Mockito.verify(userRepository).save(oldUser);
        Mockito.verify(userMapper).updateUser(oldUser, updateRequest);
    }

    @Test
    void updateUser_userNotFound_error() {
        // GIVEN
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.updateUser(1L, updateRequest));

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1005);
    }

    @Test
    void updateUser_rolesNotFound_error() {
        // GIVEN
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(oldUser));
        Mockito.when(roleRepository.findAllById(updateRequest.getRoles())).thenReturn(new ArrayList<>(Set.of()));  // No roles found

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.updateUser(1L, updateRequest));

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(2002); // Assuming an error code for roles not found
    }

    @Test
    @WithMockUser(roles = "ADMIN") // Ensure the user has ADMIN role
    void deleteUser_existingUser_success() {
        // GIVEN
        Long userId = 1L;

        // WHEN
        userService.deleteUser(userId);

        // THEN
        Mockito.verify(userRepository).deleteById(userId); // Verify delete was called with the correct ID
    }

    @Test
    @WithMockUser(roles = "USER") // Test with a non-admin role
    void deleteUser_nonAdminRole_forbidden() {
        // GIVEN
        Long userId = 1L;

        // WHEN & THEN
        assertThrows(AuthorizationDeniedException.class, () -> userService.deleteUser(userId)); // Check for AuthorizationDeniedException
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser_userDoesNotExist_fail() {
        // GIVEN
        Long userId = 1L;
        Mockito.doThrow(new EmptyResultDataAccessException(1)).when(userRepository).deleteById(userId);

        // WHEN & THEN
        assertThrows(EmptyResultDataAccessException.class, () -> userService.deleteUser(userId)); // Adjust this as per your exception handling
    }

    @Test
    @WithMockUser(roles = "ADMIN") // Simulate an admin user
    void getVendors_validRequest_success() {
        // Mock the UserRepository to return all users
        List<User> allUsers = Arrays.asList(vendorUser1, vendorUser2, customerUser);
        Mockito.when(userRepository.findAll()).thenReturn(allUsers);

        // Mock UserMapper to convert User -> UserResponse
        Mockito.when(userMapper.toUserResponse(vendorUser1)).thenReturn(vendorResponse1);
        Mockito.when(userMapper.toUserResponse(vendorUser2)).thenReturn(vendorResponse2);

        // Call the method
        List<VendorResponse> result = userService.getVendors();

        // Validate results
        Assertions.assertThat(result).hasSize(2); // Should have 2 vendors
    }

    @Test
    @WithMockUser(roles = "ADMIN") // Simulate an admin user
    void getCustomers_validRequest_success() {
        // Mock the UserRepository to return all users
        List<User> allUsers = Arrays.asList(vendorUser1, vendorUser2, customerUser);
        Mockito.when(userRepository.findAll()).thenReturn(allUsers);

        // Mock UserMapper to convert User -> UserResponse
        Mockito.when(userMapper.toUserResponse(customerUser)).thenReturn(customerResponse1);


        // Call the method
        List<UserResponse> result = userService.getCustomers();

        // Validate results
        Assertions.assertThat(result).hasSize(1); // Should have 2 vendors
        Assertions.assertThat(result.get(0).getUsername()).isEqualTo("customer1");

    }

    @Test
    @WithMockUser(roles = "USER") // Test with non-admin role
    void getVendors_nonAdminRole_forbidden() {


        // WHEN & THEN
        assertThrows(AuthorizationDeniedException.class, () -> userService.getVendors()); // Should throw an access denied exception
    }

    @Test
    @WithMockUser(roles = "VENDOR") // Test with non-admin role
    void getCustomers_nonAdminRole_forbidden() {


        // WHEN & THEN
        assertThrows(AuthorizationDeniedException.class, () -> userService.getCustomers()); // Should throw an access denied exception
    }

    @Test
    void getTotalVendors_validRequest_success() {
        // Mock the UserRepository to return all users
        List<User> allUsers = Arrays.asList(vendorUser1, vendorUser2, customerUser);
        Mockito.when(userRepository.findAll()).thenReturn(allUsers);

        // Call the method
        int result = userService.getTotalVendors();

        // Validate the result
        Assertions.assertThat(result).isEqualTo(2);
    }

    @Test
    void getTotalVendors_noVendors_success() {
        // Mock the UserRepository to return only customers (no vendors)
        List<User> allUsers = Arrays.asList(customerUser);
        Mockito.when(userRepository.findAll()).thenReturn(allUsers);

        // Call the method
        int result = userService.getTotalVendors();

        // Validate the result
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    @WithMockUser(roles = "ADMIN") // Test with an admin role
    void banUser_adminRole_success() {
        // Mock userRepository to return a user
        Mockito.when(userRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(user));

        // Call the method
        userService.banUser(1L);

        // Verify if the user is banned
        Assertions.assertThat(user.getIsActive()).isFalse();
        Mockito.verify(userRepository).save(user);
    }

    @Test
    @WithMockUser(roles = "USER") // Test with a non-admin role
    void banUser_nonAdminRole_forbidden() {
        // GIVEN
        Long userId = 1L;

        // WHEN & THEN
        Assertions.assertThatThrownBy(() -> userService.banUser(userId))
                .isInstanceOf(AuthorizationDeniedException.class);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void banUser_userNotFound_fail() {
        // Mock userRepository to return an empty optional (user not found)
        Mockito.when(userRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        // Call the method and expect an AppException
        Assertions.assertThatThrownBy(() -> userService.banUser(1L))
                .isInstanceOf(AppException.class)
                .hasMessageContaining("User not existed");
    }

    // Test for unbanUser method
    @Test
    @WithMockUser(roles = "ADMIN") // Test with an admin role
    void unbanUser_adminRole_success() {
        // Mock userRepository to return a banned user
        user.setIsActive(false);
        Mockito.when(userRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(user));

        // Call the method
        userService.unbanUser(1L);

        // Verify if the user is unbanned
        Assertions.assertThat(user.getIsActive()).isTrue();
        Mockito.verify(userRepository).save(user);
    }

    @Test
    @WithMockUser(roles = "USER") // Test with a non-admin role
    void unbanUser_nonAdminRole_forbidden() {
        // GIVEN
        Long userId = 1L;

        // WHEN & THEN
        Assertions.assertThatThrownBy(() -> userService.unbanUser(userId))
                .isInstanceOf(AuthorizationDeniedException.class);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void unbanUser_userNotFound_fail() {
        // Mock userRepository to return an empty optional (user not found)
        Mockito.when(userRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        // Call the method and expect an AppException
        Assertions.assertThatThrownBy(() -> userService.unbanUser(1L))
                .isInstanceOf(AppException.class)
                .hasMessageContaining("User not existed");
    }



}
