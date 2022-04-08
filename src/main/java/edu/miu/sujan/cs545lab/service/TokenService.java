package edu.miu.sujan.cs545lab.service;

import edu.miu.sujan.cs545lab.domain.Token;

public interface TokenService {

    void save(Token token);

    boolean isRefreshTokenExist(String refreshToken);

    void deleteRefreshToken(String refreshToken);

    void updateRefreshToken(String refreshToken, String newRefreshToken);
}
