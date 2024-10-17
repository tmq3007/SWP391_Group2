package com.shoppingapp.shoppingapp.service;

import com.nimbusds.jose.JOSEException;
import com.shoppingapp.shoppingapp.dto.request.AuthenticationRequest;
import com.shoppingapp.shoppingapp.dto.request.IntrospectRequest;
import com.shoppingapp.shoppingapp.dto.request.LogoutRequest;
import com.shoppingapp.shoppingapp.dto.request.RefreshRequest;
import com.shoppingapp.shoppingapp.dto.response.AuthenticationResponse;
import com.shoppingapp.shoppingapp.dto.response.IntrospectResponse;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.models.InvalidatedToken;
import com.shoppingapp.shoppingapp.models.User;
import com.shoppingapp.shoppingapp.repository.InvalidatedTokenRepository;
import com.shoppingapp.shoppingapp.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.ParseException;
import java.util.Optional;

@SpringBootTest
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private InvalidatedTokenRepository invalidatedTokenRepository;

    private User user;

    @BeforeEach
    void initData() {
        user = User.builder()
                .id(1L)
                .username("johndoe")
                .password(new BCryptPasswordEncoder().encode("password")) // Encoded password
                .isActive(true)
                .build();
    }

    @Test
    void isAuthenticated_validCredentials_success() {
        // GIVEN
        AuthenticationRequest request = new AuthenticationRequest("johndoe", "password");
        Mockito.when(userRepository.findByUsername("johndoe")).thenReturn(Optional.of(user));

        // WHEN
        AuthenticationResponse response = authenticationService.isAuthenticated(request);

        // THEN
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.isAuthenticated()).isTrue();
        Assertions.assertThat(response.getToken()).isNotNull(); // Check if a token was generated
    }

    @Test
    void isAuthenticated_userNotFound_fail() {
        // GIVEN
        AuthenticationRequest request = new AuthenticationRequest("unknown", "password");
        Mockito.when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        // WHEN
        var exception = Assertions.catchThrowable(() -> authenticationService.isAuthenticated(request));

        // THEN
        Assertions.assertThat(exception).isInstanceOf(AppException.class);
        Assertions.assertThat(((AppException) exception).getErrorCode()).isEqualTo(ErrorCode.USER_NOT_EXISTED);
    }

    @Test
    void isAuthenticated_inactiveUser_fail() {
        // GIVEN
        user.setIsActive(false); // Inactivate the user
        AuthenticationRequest request = new AuthenticationRequest("johndoe", "password");
        Mockito.when(userRepository.findByUsername("johndoe")).thenReturn(Optional.of(user));

        // WHEN
        var exception = Assertions.catchThrowable(() -> authenticationService.isAuthenticated(request));

        // THEN
        Assertions.assertThat(exception).isInstanceOf(AppException.class);
        Assertions.assertThat(((AppException) exception).getErrorCode()).isEqualTo(ErrorCode.USER_NOT_ACTIVE);
    }

    @Test
    void isAuthenticated_invalidPassword_fail() {
        // GIVEN
        AuthenticationRequest request = new AuthenticationRequest("johndoe", "wrongPassword");
        Mockito.when(userRepository.findByUsername("johndoe")).thenReturn(Optional.of(user));

        // WHEN
        var exception = Assertions.catchThrowable(() -> authenticationService.isAuthenticated(request));

        // THEN
        Assertions.assertThat(exception).isInstanceOf(AppException.class);
        Assertions.assertThat(((AppException) exception).getErrorCode()).isEqualTo(ErrorCode.INVALID_PASSWORD_CONFIRM);
    }

    @Test
    void introspect_validToken_success() throws JOSEException, ParseException {
        // GIVEN
        Mockito.when(userRepository.findByUsername("johndoe")).thenReturn(Optional.of(user));
        String token = authenticationService.isAuthenticated(new AuthenticationRequest("johndoe", "password")).getToken();
        IntrospectRequest request = new IntrospectRequest(token, true); // Now includes authenticated field

        // WHEN
        IntrospectResponse response = authenticationService.introspect(request);

        // THEN
        Assertions.assertThat(response.isValid()).isTrue();
    }


    @Test
    void logout_validToken_success() throws ParseException, JOSEException {
        // GIVEN
        Mockito.when(userRepository.findByUsername("johndoe")).thenReturn(Optional.of(user));
        String token = authenticationService.isAuthenticated(new AuthenticationRequest("johndoe", "password")).getToken(); // Use a valid token
        LogoutRequest request = new LogoutRequest(token);
        Mockito.when(invalidatedTokenRepository.existsById(ArgumentMatchers.anyString())).thenReturn(false);

        // WHEN
        Assertions.assertThatCode(() -> authenticationService.logout(request)).doesNotThrowAnyException();

        // THEN
        Mockito.verify(invalidatedTokenRepository).save(Mockito.any(InvalidatedToken.class));
    }

    @Test
    void refreshToken_validToken_success() throws ParseException, JOSEException {
        // GIVEN
        Mockito.when(userRepository.findByUsername("johndoe")).thenReturn(Optional.of(user));
        String token = authenticationService.isAuthenticated(new AuthenticationRequest("johndoe", "password")).getToken(); // Use a valid token
        RefreshRequest request = new RefreshRequest(token);

        // WHEN
        AuthenticationResponse response = authenticationService.refreshToken(request);

        // THEN
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.isAuthenticated()).isTrue();
    }

}
