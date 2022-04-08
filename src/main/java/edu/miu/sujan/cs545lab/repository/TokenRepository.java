package edu.miu.sujan.cs545lab.repository;

import edu.miu.sujan.cs545lab.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
  boolean existsByRefreshToken(String refreshToken);

  void deleteByPreviousRefreshToken(String refreshToken);

  Token findByRefreshToken(String refreshToken);

  Token findByPreviousRefreshToken(String refreshToken);
}
