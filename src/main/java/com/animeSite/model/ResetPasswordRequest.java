package com.animeSite.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Reset password request payload")
public class ResetPasswordRequest {

    @NotBlank
    @Schema(description = "Reset token from email", required = true)
    private String token;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Schema(description = "New password (min 6 characters)", example = "newPassword123", required = true)
    private String newPassword;
}
