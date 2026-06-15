package com.animeSite.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Forgot password request payload")
public class ForgotPasswordRequest {

    @NotBlank
    @Email
    @Schema(description = "Registered email address", example = "user@example.com", required = true)
    private String email;
}
