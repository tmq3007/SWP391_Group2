package com.shoppingapp.shoppingapp.service.Impl;

import com.shoppingapp.shoppingapp.dto.request.RejectShopRequest;
import com.shoppingapp.shoppingapp.dto.request.ResetPasswordRequest;
import com.shoppingapp.shoppingapp.dto.request.VerifyShopRequest;
import com.shoppingapp.shoppingapp.exceptions.AppException;
import com.shoppingapp.shoppingapp.exceptions.ErrorCode;
import com.shoppingapp.shoppingapp.models.User;
import com.shoppingapp.shoppingapp.repository.UserRepository;
import com.shoppingapp.shoppingapp.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;


@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 10;

    @Override
    public void resetPassword(ResetPasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXISTED));

        String newPassword = generateRandomPassword();

        // Encode the new password
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        // Save the updated user with new password
        userRepository.save(user);

        // Send email with the new password
        sendPasswordResetEmail(user.getEmail(), newPassword);
    }

    @Override
    public void verifyShop(VerifyShopRequest verifyShopRequest) {
        // Get user email from the request
        String toEmail = verifyShopRequest.getEmail();

        // Send shop verification email
        sendShopVerificationEmail(toEmail);
    }

    @Override
    public void rejectShop(RejectShopRequest rejectShopRequest) {
        // Get user email and rejection message from the request
        String toEmail = rejectShopRequest.getEmail();
        String rejectionMessage = rejectShopRequest.getMessage();

        // Send shop rejection email
        sendShopRejectionEmail(toEmail, rejectionMessage);
    }

    private void sendShopVerificationEmail(String toEmail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toEmail);
            helper.setSubject("Shop Verification");

            // HTML content for the shop verification email
            String htmlContent = "<h3>Shop Verified</h3>"
                    + "<p>Congratulations! Your shop has been successfully verified.</p>"
                    + "<p>You can now access all the features of the platform.</p>";

            helper.setText(htmlContent, true); // Enable HTML content

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Error sending shop verification email: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    private void sendShopRejectionEmail(String toEmail, String rejectionMessage) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toEmail);
            helper.setSubject("Shop Rejected");

            // HTML content for the shop rejection email
            String htmlContent = "<h3>Shop Rejected</h3>"
                    + "<p>Unfortunately, your shop could not be verified due to the following reason(s):</p>"
                    + "<p><strong>" + rejectionMessage + "</strong></p>"
                    + "<p>Please review the feedback and resubmit your application if applicable.</p>";

            helper.setText(htmlContent, true); // Enable HTML content

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Error sending shop rejection email: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }



    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            sb.append(CHAR_SET.charAt(random.nextInt(CHAR_SET.length())));
        }
        return sb.toString();
    }

    private void sendPasswordResetEmail(String toEmail, String newPassword) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toEmail);
            helper.setSubject("Password Reset Request");

            // HTML content for the email
            String htmlContent = "<h3>Password Reset Request</h3>"
                    + "<p>Your new password is: <strong>" + newPassword + "</strong></p>"
                    + "<p>Please log in and change your password immediately for security purposes.</p>";

            helper.setText(htmlContent, true); // Set 'true' to enable HTML content

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Error sending email: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}