package com.animeSite.controller;

import com.animeSite.core.model.ApiResponse;
import com.animeSite.model.ChangePasswordRequest;
import com.animeSite.model.UpdateProfileRequest;
import com.animeSite.persist.User;
import com.animeSite.repo.UserRepository;
import com.animeSite.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "User profile management endpoints")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    @Operation(summary = "Get my profile", description = "Returns the authenticated user's profile.")
    public ResponseEntity<ApiResponse<User>> getProfile(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(null);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PutMapping("/me")
    @Operation(summary = "Update profile", description = "Updates the authenticated user's name.")
    public ResponseEntity<ApiResponse<User>> updateProfile(@Valid @RequestBody UpdateProfileRequest request,
                                                            Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User updated = userService.updateProfile(user.getId(), request);
        updated.setPassword(null);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }

    @PutMapping("/me/password")
    @Operation(summary = "Change password", description = "Changes the authenticated user's password.")
    public ResponseEntity<ApiResponse<String>> changePassword(@Valid @RequestBody ChangePasswordRequest request,
                                                               Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userService.changePassword(user.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("Password changed successfully"));
    }

    @DeleteMapping("/me")
    @Operation(summary = "Delete account", description = "Deletes the authenticated user's account.")
    public ResponseEntity<ApiResponse<String>> deleteAccount(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userService.deleteAccount(user.getId());
        return ResponseEntity.ok(ApiResponse.success("Account deleted successfully"));
    }
}
