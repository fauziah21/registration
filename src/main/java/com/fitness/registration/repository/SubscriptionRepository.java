package com.fitness.registration.repository;

import com.fitness.registration.model.SubscriptionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<SubscriptionModel, Integer> {
    Optional<SubscriptionModel> findBySubId(int subId);

}
