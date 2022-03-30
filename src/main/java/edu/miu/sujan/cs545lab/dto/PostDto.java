package edu.miu.sujan.cs545lab.dto;

import lombok.Data;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String author;
}
