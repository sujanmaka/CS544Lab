package edu.miu.sujan.cs545lab.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostDto {
  private Long id;
  private String title;
  private String content;
  private String author;
  List<CommentDto> comments;
}
