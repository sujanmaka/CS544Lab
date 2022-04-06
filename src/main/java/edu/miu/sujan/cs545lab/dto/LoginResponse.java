package edu.miu.sujan.cs545lab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
  private String accessToken;
  private String refreshToken;
}
