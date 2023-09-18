package com.fitness.registration.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProgramDTO {
    private int subId;
    private String subMenu;
    private int subPrice;
    private int subDuration;
    private List<ProgramDetailDTO> detailDTO;
    private List<ProgramScheduleDTO> scheduleDTO;
    private SubscriptionStatusDTO subscriptionStatusDTO;
}
