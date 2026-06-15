package com.animeSite.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Review request payload")
public class ReviewRequest {

    @NotNull(message = "Star rating is required")
    @Min(value = 1, message = "Star rating must be at least 1")
    @Max(value = 5, message = "Star rating must be at most 5")
    @Schema(description = "Star rating (1-5)", example = "4", required = true)
    private int starRating;

    @Schema(description = "Review comment", example = "Great anime, highly recommended!")
    private String comment;
}
