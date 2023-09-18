package com.fitness.registration.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "regs_subscription")
public class SubscriptionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_id")
    private int subId;

    @Column(name = "sub_menu")
    private String subMenu;
    @Column(name = "sub_price")
    private int subPrice;
    @Column(name = "sub_duration")
    private int subDuration;

}
