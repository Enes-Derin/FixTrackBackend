package com.enesderin.FixTrackBackend.service;

import com.enesderin.FixTrackBackend.dto.auth.*;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
