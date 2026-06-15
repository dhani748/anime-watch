package com.animeSite.controller;

import com.animeSite.constant.Role;
import com.animeSite.constant.WatchlistStatus;
import com.animeSite.core.model.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/codes")
@Tag(name = "Codes", description = "Static code/lookup data endpoints")
public class CodeController {

    @GetMapping("/roles")
    @Operation(summary = "Get roles", description = "Returns available user roles.")
    public ResponseEntity<ApiResponse<List<Map<String, String>>>> getRoles() {
        List<Map<String, String>> codes = List.of(
                Map.of("code", Role.ROLE_USER.name(), "description", "Standard user"),
                Map.of("code", Role.ROLE_ADMIN.name(), "description", "Administrator")
        );
        return ResponseEntity.ok(ApiResponse.success(codes));
    }

    @GetMapping("/watchlist-statuses")
    @Operation(summary = "Get watchlist statuses", description = "Returns available watchlist statuses.")
    public ResponseEntity<ApiResponse<List<Map<String, String>>>> getWatchlistStatuses() {
        List<Map<String, String>> codes = List.of(
                Map.of("code", WatchlistStatus.WATCHING.name(), "description", "Currently watching"),
                Map.of("code", WatchlistStatus.COMPLETED.name(), "description", "Finished watching"),
                Map.of("code", WatchlistStatus.PLAN_TO_WATCH.name(), "description", "Plan to watch")
        );
        return ResponseEntity.ok(ApiResponse.success(codes));
    }
}
