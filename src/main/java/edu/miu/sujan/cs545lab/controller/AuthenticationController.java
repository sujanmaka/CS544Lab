package edu.miu.sujan.cs545lab.controller;

import edu.miu.sujan.cs545lab.dto.LoginRequest;
import edu.miu.sujan.cs545lab.dto.LoginResponse;
import edu.miu.sujan.cs545lab.dto.RefreshTokenRequest;
import edu.miu.sujan.cs545lab.service.UaaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authenticate")
public class AuthenticationController {

  private final UaaService uaaService;

  public AuthenticationController(UaaService uaaService) {
    this.uaaService = uaaService;
  }

  @PostMapping
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    return new ResponseEntity<>(uaaService.login(loginRequest), HttpStatus.ACCEPTED);
  }

  @PostMapping("/refresh")
  public LoginResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
    return uaaService.refreshToken(refreshTokenRequest);
  }
}
