package com.animeSite.service;
import com.animeSite.service.impl.UserServiceImpl;

import com.animeSite.model.ChangePasswordRequest;
import com.animeSite.model.UpdateProfileRequest;
import com.animeSite.persist.User;
import com.animeSite.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void getProfile_ShouldReturnUser() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);
        user.setEmail("test@example.com");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.getProfile(id);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void updateProfile_ShouldChangeName() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);
        user.setName("Old");

        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setName("New");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User result = userService.updateProfile(id, request);
        assertEquals("New", result.getName());
    }

    @Test
    void changePassword_ShouldThrow_WhenCurrentPasswordWrong() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);
        user.setPassword("encoded-old");

        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCurrentPassword("wrong");
        request.setNewPassword("newPass123");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encoded-old")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.changePassword(id, request));
        verify(userRepository, never()).save(any());
    }

    @Test
    void changePassword_ShouldSucceed_WhenCurrentPasswordCorrect() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);
        user.setPassword("encoded-old");

        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCurrentPassword("correct");
        request.setNewPassword("newPass123");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("correct", "encoded-old")).thenReturn(true);
        when(passwordEncoder.encode("newPass123")).thenReturn("encoded-new");

        userService.changePassword(id, request);

        verify(userRepository).save(any());
        assertEquals("encoded-new", user.getPassword());
    }
}
