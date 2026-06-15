package com.animeSite.controller;

import com.animeSite.core.model.ApiResponse;
import com.animeSite.model.UpdateStatusRequest;
import com.animeSite.model.WatchlistRequest;
import com.animeSite.persist.User;
import com.animeSite.persist.Watchlist;
import com.animeSite.repo.UserRepository;
import com.animeSite.service.WatchlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/watchlist")
@Tag(name = "Watchlist", description = "User watchlist endpoints")
public class WatchlistController {

    private final WatchlistService watchlistService;
    private final UserRepository userRepository;

    public WatchlistController(WatchlistService watchlistService, UserRepository userRepository) {
        this.watchlistService = watchlistService;
        this.userRepository = userRepository;
    }

    @PostMapping
    @Operation(summary = "Add to watchlist", description = "Adds an anime to the user's watchlist.")
    public ResponseEntity<ApiResponse<Watchlist>> addToWatchlist(@Valid @RequestBody WatchlistRequest request,
                                                                  Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Watchlist entry = watchlistService.addToWatchlist(user.getId(), request.getAnimeId(), request.getStatus());
        return ResponseEntity.ok(ApiResponse.success(entry));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update watchlist status", description = "Updates the status of a watchlist entry.")
    public ResponseEntity<ApiResponse<Watchlist>> updateStatus(
            @Parameter(description = "Watchlist entry ID", required = true) @PathVariable UUID id,
            @Valid @RequestBody UpdateStatusRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Watchlist entry = watchlistService.updateStatus(user.getId(), id, request.getStatus());
        return ResponseEntity.ok(ApiResponse.success(entry));
    }

    @GetMapping
    @Operation(summary = "Get my watchlist", description = "Returns all watchlist entries for the authenticated user.")
    public ResponseEntity<ApiResponse<List<Watchlist>>> getMyWatchlist(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Watchlist> list = watchlistService.getMyWatchlist(user.getId());
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove from watchlist", description = "Removes a watchlist entry by ID.")
    public ResponseEntity<ApiResponse<String>> removeFromWatchlist(
            @Parameter(description = "Watchlist entry ID", required = true) @PathVariable UUID id,
            Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        watchlistService.removeFromWatchlist(user.getId(), id);
        return ResponseEntity.ok(ApiResponse.success("Removed from watchlist"));
    }
}
