package com.animeSite.service;
import com.animeSite.service.impl.AuthServiceImpl;

import com.animeSite.model.LoginRequest;
import com.animeSite.model.RegisterRequest;
import com.animeSite.persist.User;
import com.animeSite.constant.Role;
import com.animeSite.repo.EmailVerificationTokenRepository;
import com.animeSite.repo.PasswordResetTokenRepository;
import com.animeSite.repo.RefreshTokenRepository;
import com.animeSite.repo.UserRepository;
import com.animeSite.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private EmailVerificationTokenRepository tokenRepository;
    @Mock private RefreshTokenRepository refreshTokenRepository;
    @Mock private PasswordResetTokenRepository passwordResetTokenRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private JwtTokenProvider jwtTokenProvider;
    @Mock private EmailService emailService;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(userRepository, tokenRepository, refreshTokenRepository,
                passwordResetTokenRepository, passwordEncoder, authenticationManager,
                jwtTokenProvider, emailService);
    }

    @Test
    void register_ShouldCreateUser_WhenEmailNotTaken() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Test");
        request.setEmail("test@example.com");
        request.setPassword("password123");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User result = authService.register(request);

        assertEquals("test@example.com", result.getEmail());
        assertEquals("encoded", result.getPassword());
        assertEquals(Role.ROLE_USER, result.getRole());
        assertFalse(result.isVerified());
        verify(tokenRepository).save(any());
        verify(emailService).sendVerificationEmail(anyString(), anyString());
    }

    @Test
    void register_ShouldThrow_WhenEmailTaken() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("taken@example.com");
        when(userRepository.existsByEmail("taken@example.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> authService.register(request));
        verify(userRepository, never()).save(any());
    }

    @Test
    void verifyEmail_ShouldThrow_WhenInvalidToken() {
        when(tokenRepository.findByToken("invalid")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> authService.verifyEmail("invalid"));
    }

    @Test
    void forgotPassword_ShouldNotThrow_WhenEmailNotFound() {
        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> authService.forgotPassword("missing@example.com"));
        verify(passwordResetTokenRepository, never()).save(any());
    }
}
