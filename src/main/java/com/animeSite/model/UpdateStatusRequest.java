package com.animeSite.model;

import com.animeSite.constant.WatchlistStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Watchlist status update payload")
public class UpdateStatusRequest {

    @NotNull
    @Schema(description = "New watch status", example = "WATCHING", required = true)
    private WatchlistStatus status;
}
