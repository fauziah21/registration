package com.fitness.registration.repository;

import com.fitness.registration.model.SubscriptionDetailModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionDetailRepository extends JpaRepository<SubscriptionDetailModel,Integer> {
    List<SubscriptionDetailModel> findBySubscriptionId(int subscriptionId);
}
