package com.animeSite.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Change password request payload")
public class ChangePasswordRequest {

    @NotBlank
    @Schema(description = "Current password", required = true)
    private String currentPassword;

    @NotBlank
    @Size(min = 6, message = "New password must be at least 6 characters")
    @Schema(description = "New password (min 6 characters)", example = "newPassword123", required = true)
    private String newPassword;
}
