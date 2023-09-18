package com.fitness.registration.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "regs_subs_member")
public class SubscriptionMemberModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subs_member_id")
    private int subMemberId;

    @Column(name = "subscription_id")
    private int subscriptionId;

    @Column(name = "subs_member_status", columnDefinition = "char")
    private String subsMemberStatus;

    @Column(name = "subs_member_session")
    private int subsMemberSession;

    @Column(name = "subs_member_account_id")
    private Long subsMemberAccountId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", shape = JsonFormat.Shape.STRING, locale = "in_ID")
    @Column(name = "created_at")
    private LocalDateTime CreatedAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", shape = JsonFormat.Shape.STRING, locale = "in_ID")
    @Column(name = "updated_at")
    private LocalDateTime UpdatedAt;
}
