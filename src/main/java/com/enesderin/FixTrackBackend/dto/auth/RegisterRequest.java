package com.enesderin.FixTrackBackend.dto.auth;

import com.enesderin.FixTrackBackend.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    @Email
    private String email;
    @NotNull
    private Role role;
}
