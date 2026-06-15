package com.animeSite.controller;

import com.animeSite.core.model.ApiResponse;
import com.animeSite.persist.Favorites;
import com.animeSite.persist.User;
import com.animeSite.repo.UserRepository;
import com.animeSite.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/favorites")
@Tag(name = "Favorites", description = "User favorites endpoints")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserRepository userRepository;

    public FavoriteController(FavoriteService favoriteService, UserRepository userRepository) {
        this.favoriteService = favoriteService;
        this.userRepository = userRepository;
    }

    @PostMapping("/{animeId}")
    @Operation(summary = "Add to favorites", description = "Adds an anime to the user's favorites.")
    public ResponseEntity<ApiResponse<Favorites>> addToFavorites(
            @Parameter(description = "Anime ID", required = true) @PathVariable UUID animeId,
            Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Favorites fav = favoriteService.addToFavorites(user.getId(), animeId);
        return ResponseEntity.ok(ApiResponse.success(fav));
    }

    @GetMapping
    @Operation(summary = "Get my favorites", description = "Returns all favorites for the authenticated user.")
    public ResponseEntity<ApiResponse<List<Favorites>>> getMyFavorites(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Favorites> list = favoriteService.getMyFavorites(user.getId());
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @DeleteMapping("/{animeId}")
    @Operation(summary = "Remove from favorites", description = "Removes an anime from the user's favorites.")
    public ResponseEntity<ApiResponse<String>> removeFromFavorites(
            @Parameter(description = "Anime ID", required = true) @PathVariable UUID animeId,
            Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        favoriteService.removeFromFavorites(user.getId(), animeId);
        return ResponseEntity.ok(ApiResponse.success("Removed from favorites"));
    }
}
