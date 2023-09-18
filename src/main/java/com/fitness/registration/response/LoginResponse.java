package com.fitness.registration.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse extends BaseResponse {
    private JwtResponse data;
}
