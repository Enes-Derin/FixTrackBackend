package com.enesderin.FixTrackBackend.service.impl;

import com.enesderin.FixTrackBackend.dto.auth.*;
import com.enesderin.FixTrackBackend.exception.ErrorMessage;
import com.enesderin.FixTrackBackend.exception.MessageType;
import com.enesderin.FixTrackBackend.exception.handler.BaseException;
import com.enesderin.FixTrackBackend.model.RefreshToken;
import com.enesderin.FixTrackBackend.model.User;
import com.enesderin.FixTrackBackend.repository.RefreshTokenRepository;
import com.enesderin.FixTrackBackend.repository.UserRepository;
import com.enesderin.FixTrackBackend.security.JwtService;
import com.enesderin.FixTrackBackend.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthserviceImpl implements AuthService{


    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserRepository userRepository;

    private AuthenticationProvider authenticationProvider;

    private JwtService jwtService;

    private RefreshTokenRepository refreshTokenRepository;


    private User createUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setRole(registerRequest.getRole());
        return user;
    }

    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
        return refreshToken;
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        User saved = this.userRepository.save(this.createUser(registerRequest));
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setUsername(saved.getUsername());
        registerResponse.setRole(saved.getRole().toString());
        return registerResponse;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            authenticationProvider.authenticate(authenticationToken);

            Optional<User> optionalUser = this.userRepository.findByUsername(loginRequest.getUsername());
            String accessToken= jwtService.generateToken(optionalUser.get());
            RefreshToken refreshToken = refreshTokenRepository.save(createRefreshToken(optionalUser.get()));
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setAccessToken(accessToken);
            loginResponse.setRefreshToken(refreshToken.getRefreshToken());
            loginResponse.setRole(optionalUser.get().getRole());
            return loginResponse;
        }catch (Exception e){
            throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID));
        }
    }

    public boolean isValidRefreshToken(Date expiryDate) {
        return new Date().before(expiryDate);
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByRefreshToken(refreshTokenRequest.getRefreshToken());
        if (optionalRefreshToken.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_NOT_FOUND, refreshTokenRequest.getRefreshToken()));
        }
        if (!isValidRefreshToken(optionalRefreshToken.get().getExpiryDate())) {
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_IS_EXPIRED, refreshTokenRequest.getRefreshToken()));
        }
        User user = optionalRefreshToken.get().getUser();
        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = this.refreshTokenRepository.save(createRefreshToken(user));
        return new RefreshTokenResponse(accessToken, refreshToken.getRefreshToken());
    }
}
