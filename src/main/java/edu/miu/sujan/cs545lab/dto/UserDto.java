package edu.miu.sujan.cs545lab.dto;

import edu.miu.sujan.cs545lab.enums.RoleType;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
  private Long id;
  private String name;
  private String email;
  private String password;
  private List<PostDto> posts;
  private List<RoleType> roles;
}
