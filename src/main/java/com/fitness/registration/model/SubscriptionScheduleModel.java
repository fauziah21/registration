package com.fitness.registration.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "regs_subs_schedule")
public class SubscriptionScheduleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subs_schedule_id")
    private int subsScheduleId;

    @Column(name = "subscription_id")
    private int subscriptionId;

    @Column(name = "subs_schedule")
    private String subsSchedule;
}
