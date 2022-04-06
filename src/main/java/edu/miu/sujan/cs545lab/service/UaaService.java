package edu.miu.sujan.cs545lab.service;

import edu.miu.sujan.cs545lab.dto.LoginRequest;
import edu.miu.sujan.cs545lab.dto.LoginResponse;
import edu.miu.sujan.cs545lab.dto.RefreshTokenRequest;

public interface UaaService {
  LoginResponse login(LoginRequest loginRequest);

  LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
