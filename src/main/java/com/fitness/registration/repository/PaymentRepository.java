package com.fitness.registration.repository;

import com.fitness.registration.model.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentModel,Integer> {
    Optional<PaymentModel> findByAccountId(Long accountId);
}
