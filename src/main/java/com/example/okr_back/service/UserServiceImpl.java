package com.example.okr_back.service;

import com.example.okr_back.dto.LoginRequest;
import com.example.okr_back.dto.LoginResponse;
import com.example.okr_back.dto.RefreshTokenRequest;
import com.example.okr_back.infra.security.CustomUserDetailsService;
import com.example.okr_back.infra.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public UserServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        log.info("Attempting to login user with email: {}", loginRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwt = tokenProvider.generateToken(userDetails);
        String refreshToken = tokenProvider.generateRefreshToken(userDetails);
        log.info("User {} logged in successfully", loginRequest.getEmail());
        return new LoginResponse(jwt, refreshToken);
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        log.info("Attempting to refresh token");
        if (tokenProvider.validateToken(refreshTokenRequest.getRefreshToken())) {
            String username = tokenProvider.getUsernameFromJWT(refreshTokenRequest.getRefreshToken());
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            String newJwt = tokenProvider.generateToken(userDetails);
            log.info("Token refreshed successfully for user {}", username);
            return new LoginResponse(newJwt, refreshTokenRequest.getRefreshToken());
        }
        log.warn("Invalid refresh token");
        // You might want to throw a custom exception here
        return null;
    }
}
