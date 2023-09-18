package com.fitness.registration.repository;

import com.fitness.registration.model.SubscriptionMemberModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionMemberRepository extends JpaRepository<SubscriptionMemberModel,Integer> {
    Optional<SubscriptionMemberModel> findBySubscriptionIdAndSubsMemberAccountId(int subscriptionId,Long subsMemberAccountId);
    Optional<SubscriptionMemberModel> findBySubMemberId(int subMemberId);
    Optional<SubscriptionMemberModel> findBySubsMemberAccountId(Long subsMemberAccountId);
}
