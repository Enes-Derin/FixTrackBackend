package com.enesderin.FixTrackBackend.dto.auth;

import com.enesderin.FixTrackBackend.model.Role;
import lombok.Data;

@Data
public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private Role role;
}
