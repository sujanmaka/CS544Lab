package edu.miu.sujan.cs545lab.dto;

import lombok.Data;

@Data
public class FilterDto {
  private String author;
  private int numberOfPost;
  private String title;
}
