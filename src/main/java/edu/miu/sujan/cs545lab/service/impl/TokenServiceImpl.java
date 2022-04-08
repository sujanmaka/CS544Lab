package edu.miu.sujan.cs545lab.service.impl;

import edu.miu.sujan.cs545lab.domain.Token;
import edu.miu.sujan.cs545lab.repository.TokenRepository;
import edu.miu.sujan.cs545lab.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

  private TokenRepository tokenRepository;

  @Autowired
  public void setTokenRepository(TokenRepository tokenRepository) {
    this.tokenRepository = tokenRepository;
  }

  @Override
  public void save(Token token) {
    tokenRepository.save(token);
  }

  @Override
  public boolean isRefreshTokenExist(String refreshToken) {
    return tokenRepository.existsByRefreshToken(refreshToken);
  }

  @Override
  public void deleteRefreshToken(String refreshToken) {
    Token token = tokenRepository.findByPreviousRefreshToken(refreshToken);
    if (token != null) {
      tokenRepository.delete(token);
    }
  }

  @Override
  public void updateRefreshToken(String refreshToken, String newRefreshToken) {
    Token token = tokenRepository.findByRefreshToken(refreshToken);
    token.setPreviousRefreshToken(token.getRefreshToken());
    token.setRefreshToken(newRefreshToken);
    tokenRepository.save(token);
  }
}
