package com.shoppingapp.shoppingapp.service;

import com.shoppingapp.shoppingapp.dto.request.ResetPasswordRequest;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.models.User;
import com.shoppingapp.shoppingapp.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JavaMailSender mailSender;

    private ResetPasswordRequest resetPasswordRequest;

    private User user;

    @BeforeEach
    void initData() {
        // Prepare ResetPasswordRequest
        resetPasswordRequest = ResetPasswordRequest.builder()
                .email("user@example.com")
                .build();

        // Prepare a user object
        user = User.builder()
                .id(1L)
                .email("user@example.com")
                .username("johndoe")
                .password("oldEncodedPassword")
                .build();
    }

    @Test
    void sendResetPasswordEmail_emailExists_success() throws Exception {
        // Mock userRepository to return the user
        Mockito.when(userRepository.findByEmail(resetPasswordRequest.getEmail()))
                .thenReturn(Optional.of(user));

        // Mock passwordEncoder to return a new encoded password
        Mockito.when(passwordEncoder.encode(ArgumentMatchers.anyString()))
                .thenReturn("newEncodedPassword");

        // Mock sending email
        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        Mockito.when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Call the method
        emailService.resetPassword(resetPasswordRequest);

        // Verify that a new password was set
        Mockito.verify(userRepository).save(user);
        Assertions.assertThat(user.getPassword()).isEqualTo("newEncodedPassword");

        // Verify that the email was sent
        Mockito.verify(mailSender).send(mimeMessage);
    }

    @Test
    void sendResetPasswordEmail_emailNotFound_fail() {
        // Mock userRepository to return empty (user not found)
        Mockito.when(userRepository.findByEmail(resetPasswordRequest.getEmail()))
                .thenReturn(Optional.empty());

        // When the method is called, it should throw an AppException
        var exception = assertThrows(AppException.class, () -> emailService.resetPassword(resetPasswordRequest));

        // Validate the exception
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(2004);
    }

    @Test
    void sendResetPasswordEmail_emailSendingFails_fail() throws Exception {
        // Mock userRepository to return the user
        Mockito.when(userRepository.findByEmail(resetPasswordRequest.getEmail()))
                .thenReturn(Optional.of(user));

        // Mock passwordEncoder to return a new encoded password
        Mockito.when(passwordEncoder.encode(ArgumentMatchers.anyString()))
                .thenReturn("newEncodedPassword");

        // Mock sending email, but throw an exception
        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        Mockito.when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        Mockito.doThrow(new RuntimeException("Email sending failed"))
                .when(mailSender).send(mimeMessage);

        // Call the method and expect an AppException
        var exception = assertThrows(RuntimeException.class, () -> emailService.resetPassword(resetPasswordRequest));

        // Validate the exception
        Assertions.assertThat(exception.getMessage()).contains("Email sending failed");
    }
}
