package edu.miu.sujan.cs545lab.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String name;
    private List<PostDto> posts;
}
