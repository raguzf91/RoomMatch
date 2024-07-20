package com.raguzf.roommatch.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {

    @Email
    @NotEmpty(message = "Email cannot be empty")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
