package com.fitness.registration.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "regs_subs_detail")
public class SubscriptionDetailModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subs_detail_id")
    private int subsDetailId;

    @Column(name = "subscription_id")
    private int subscriptionId;

    @Column(name = "subs_detail_desc", columnDefinition = "text")
    private String subsDetailDesc;

    @Column(name = "subs_detail_duration")
    private int subsDetailDuration;

}
