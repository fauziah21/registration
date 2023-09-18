package com.fitness.registration.service;

import com.fitness.registration.jwt.TokenRefreshException;
import com.fitness.registration.model.AccountModel;
import com.fitness.registration.model.RefreshToken;
import com.fitness.registration.repository.AccountRepository;
import com.fitness.registration.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private static final int refreshTokenDurationMs = 120000;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(String accountEmail) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(accountRepository.findByAccountEmail(accountEmail).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long accountId) {
        Optional<AccountModel> accountIdOpt = accountRepository.findByAccountId(accountId);
        return refreshTokenRepository.deleteByUser(accountRepository.findByAccountId(accountIdOpt.get().getAccountId()).get());
    }

}
