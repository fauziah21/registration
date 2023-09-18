package com.fitness.registration.repository;

import com.fitness.registration.model.AccountModel;
import com.fitness.registration.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(AccountModel user);

    List<RefreshToken> findByUser(AccountModel user);
}
