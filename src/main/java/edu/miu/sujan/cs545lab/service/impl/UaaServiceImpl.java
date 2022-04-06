package edu.miu.sujan.cs545lab.service.impl;

import edu.miu.sujan.cs545lab.dto.LoginRequest;
import edu.miu.sujan.cs545lab.dto.LoginResponse;
import edu.miu.sujan.cs545lab.dto.RefreshTokenRequest;
import edu.miu.sujan.cs545lab.exception.InvalidUserException;
import edu.miu.sujan.cs545lab.service.UaaService;
import edu.miu.sujan.cs545lab.util.JwtHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UaaServiceImpl implements UaaService {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final JwtHelper jwtHelper;

  @Override
  public LoginResponse login(LoginRequest loginRequest) {
    try {
      var result =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  loginRequest.getEmail(), loginRequest.getPassword()));
      final UserDetails userDetails =
          userDetailsService.loadUserByUsername(loginRequest.getEmail());

      final String accessToken = jwtHelper.generateToken(loginRequest.getEmail());
      final String refreshToken = jwtHelper.generateRefreshToken(loginRequest.getEmail());
      return new LoginResponse(accessToken, refreshToken);
    } catch (BadCredentialsException e) {
      log.info("Bad Credentials");
      throw new InvalidUserException("Invalid credentials.");
    }
  }

  @Override
  public LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
    boolean isRefreshTokenValid = jwtHelper.validateToken(refreshTokenRequest.getRefreshToken());
    if (isRefreshTokenValid) {
      final String accessToken =
          jwtHelper.generateToken(jwtHelper.getSubject(refreshTokenRequest.getRefreshToken()));
      var loginResponse = new LoginResponse(accessToken, refreshTokenRequest.getRefreshToken());
      return loginResponse;
    }
    return new LoginResponse();
  }
}
