package com.fitness.registration.response;

import com.fitness.registration.dto.ListProgramDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ListProgramResponse extends BaseResponse{
    private List<ListProgramDTO> data;
}
