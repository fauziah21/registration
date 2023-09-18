package com.fitness.registration.repository;

import com.fitness.registration.model.SubscriptionScheduleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionScheduleRepository extends JpaRepository<SubscriptionScheduleModel,Integer> {
    List<SubscriptionScheduleModel> findBySubscriptionId(int subscriptionId);
}
