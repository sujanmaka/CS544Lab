package edu.miu.sujan.cs545lab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
  @JsonProperty("username")
  private String email;
  private String password;
}
