package com.example.okr_back.service;

import com.example.okr_back.dto.LoginRequest;
import com.example.okr_back.dto.LoginResponse;
import com.example.okr_back.dto.RefreshTokenRequest;

public interface IUserService {
    LoginResponse login(LoginRequest loginRequest);
    LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
