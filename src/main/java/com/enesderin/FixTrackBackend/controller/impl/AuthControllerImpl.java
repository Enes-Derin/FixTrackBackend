package com.enesderin.FixTrackBackend.controller.impl;


import com.enesderin.FixTrackBackend.controller.AuthController;
import com.enesderin.FixTrackBackend.controller.RestBaseController;
import com.enesderin.FixTrackBackend.controller.RootEntity;
import com.enesderin.FixTrackBackend.dto.auth.*;
import com.enesderin.FixTrackBackend.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
public class AuthControllerImpl extends RestBaseController implements AuthController {

    private AuthService authService;

    @PostMapping("/register")
    @Override
    public RootEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return success(authService.register(registerRequest));
    }

    @PostMapping("/login")
    @Override
    public RootEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return success(authService.login(loginRequest));
    }

    @PostMapping("/refreshToken")
    @Override
    public RootEntity<RefreshTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return success(authService.refreshToken(refreshTokenRequest));
    }
}
