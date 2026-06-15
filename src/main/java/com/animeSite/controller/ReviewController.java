package com.animeSite.controller;

import com.animeSite.core.model.ApiResponse;
import com.animeSite.model.ReviewRequest;
import com.animeSite.persist.Review;
import com.animeSite.persist.User;
import com.animeSite.repo.UserRepository;
import com.animeSite.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Reviews", description = "User review management endpoints")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserRepository userRepository;

    public ReviewController(ReviewService reviewService, UserRepository userRepository) {
        this.reviewService = reviewService;
        this.userRepository = userRepository;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit review", description = "Edits the authenticated user's review.")
    public ResponseEntity<ApiResponse<Review>> editReview(
            @Parameter(description = "Review ID", required = true) @PathVariable UUID id,
            @Valid @RequestBody ReviewRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Review review = reviewService.updateReview(id, user.getId(), request);
        return ResponseEntity.ok(ApiResponse.success(review));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete own review", description = "Deletes the authenticated user's review.")
    public ResponseEntity<ApiResponse<String>> deleteReview(
            @Parameter(description = "Review ID", required = true) @PathVariable UUID id,
            Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        reviewService.deleteUserReview(id, user.getId());
        return ResponseEntity.ok(ApiResponse.success("Review deleted successfully"));
    }
}
