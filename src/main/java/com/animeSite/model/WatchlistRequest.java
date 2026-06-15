package com.animeSite.model;

import com.animeSite.constant.WatchlistStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Watchlist request payload")
public class WatchlistRequest {

    @NotNull
    @Schema(description = "Anime ID", example = "550e8400-e29b-41d4-a716-446655440000", required = true)
    private UUID animeId;

    @NotNull
    @Schema(description = "Watch status", example = "PLAN_TO_WATCH", required = true)
    private WatchlistStatus status;
}
