package com.fitness.registration.repository;

import com.fitness.registration.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Integer> {

    Optional<AccountModel> findByAccountEmail(String accountEmail);
    Optional<AccountModel> findByAccountId(Long accountId);

    Optional<AccountModel> findByAccountName(String accountName);
}
