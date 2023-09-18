package com.fitness.registration.response;

import com.fitness.registration.dto.ProgramDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProgramResponse extends BaseResponse{
    private ProgramDTO data;

}
