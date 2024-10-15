package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.UserCreationRequest;
import com.shoppingapp.shoppingapp.dto.request.UserUpdateRequest;
import com.shoppingapp.shoppingapp.dto.response.UserResponse;
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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    }


    @Test
    void createUser_validRequest_success() {
        //GIVEN
        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(ArgumentMatchers.anyString())).thenReturn(false);
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
        Mockito.when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.of(user));

        var response = userService.getMyInfo();

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

}
