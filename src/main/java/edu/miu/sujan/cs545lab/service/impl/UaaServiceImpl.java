package edu.miu.sujan.cs545lab.service.impl;

import edu.miu.sujan.cs545lab.domain.Token;
import edu.miu.sujan.cs545lab.dto.LoginRequest;
import edu.miu.sujan.cs545lab.dto.LoginResponse;
import edu.miu.sujan.cs545lab.dto.RefreshTokenRequest;
import edu.miu.sujan.cs545lab.exception.InvalidUserException;
import edu.miu.sujan.cs545lab.service.TokenService;
import edu.miu.sujan.cs545lab.service.UaaService;
import edu.miu.sujan.cs545lab.util.JwtUtil;
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
  private final JwtUtil jwtUtil;
  private final TokenService tokenService;

  @Override
  public LoginResponse login(LoginRequest loginRequest) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginRequest.getEmail(), loginRequest.getPassword()));
    } catch (BadCredentialsException e) {
      log.info("Bad Credentials");
      throw new InvalidUserException("Invalid credentials.");
    }
    final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

    final String accessToken = jwtUtil.generateToken(userDetails);
    //    final String accessToken = jwtTokenUtil.generateToken(userDetails);
    final String refreshToken = jwtUtil.generateRefreshToken(loginRequest.getEmail());
    Token token = new Token(refreshToken, refreshToken);
    token.setRefreshToken(refreshToken);
    tokenService.save(token);
    return new LoginResponse(accessToken, refreshToken);
  }

  @Override
  public LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
    boolean isRefreshTokenValid = jwtUtil.validateToken(refreshTokenRequest.getRefreshToken());
    if (isRefreshTokenValid) {
      boolean isAccessTokenExpired = jwtUtil.isTokenExpired(refreshTokenRequest.getAccessToken());
      isAccessTokenExpired = true;
      if (isAccessTokenExpired) {
        // if refreshToken doesn't exist in DB,
        // then the passed token should be the previous_refreshToken
        if (!tokenService.isRefreshTokenExist(refreshTokenRequest.getRefreshToken())) {
          // deleting token object based on previous_refreshToken
          // after this step, re-login is required
          tokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
          throw new InvalidUserException("Invalid Refresh Token");
        }
        // generate new refresh token and update it into token table
        String newRefreshToken =
            jwtUtil.generateNewRefreshToken(refreshTokenRequest.getAccessToken());
        tokenService.updateRefreshToken(refreshTokenRequest.getRefreshToken(), newRefreshToken);

        System.out.println("ACCESS TOKEN IS EXPIRED");
        final String newAccessToken =
            jwtUtil.doGenerateToken(jwtUtil.getSubject(refreshTokenRequest.getRefreshToken()));
        new LoginResponse(newAccessToken, newRefreshToken);
      } else {
        System.out.println("ACCESS TOKEN IS NOT EXPIRED");
      }
    }
    return new LoginResponse(
        refreshTokenRequest.getAccessToken(), refreshTokenRequest.getRefreshToken());
  }
}
