package edu.miu.sujan.cs545lab.domain;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Principal {
  private Long userId;
  private String userName;
}
