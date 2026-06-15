package com.animeSite.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Profile update request payload")
public class UpdateProfileRequest {

    @NotBlank
    @Schema(description = "Full name", example = "John Doe", required = true)
    private String name;
}
