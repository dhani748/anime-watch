package com.animeSite.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "News article request payload")
public class NewsRequest {

    @NotBlank
    @Schema(description = "Article title", example = "New Anime Season Announced", required = true)
    private String title;

    @NotBlank
    @Schema(description = "Article content", required = true)
    private String content;

    @Schema(description = "Optional image URL", example = "https://example.com/image.jpg")
    private String imageUrl;
}
