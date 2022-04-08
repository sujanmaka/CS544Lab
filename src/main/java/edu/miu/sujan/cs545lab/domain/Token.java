package edu.miu.sujan.cs545lab.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String previousRefreshToken;
  private String refreshToken;

  public Token(String previousRefreshToken, String refreshToken) {
    this.previousRefreshToken = previousRefreshToken;
    this.refreshToken = refreshToken;
  }
}
