package com.enesderin.FixTrackBackend.controller;


import com.enesderin.FixTrackBackend.dto.auth.*;

public interface AuthController {
    RootEntity<RegisterResponse> register(RegisterRequest registerRequest);
    RootEntity<LoginResponse> login(LoginRequest loginRequest);
    RootEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest refreshTokenRequest);
}
