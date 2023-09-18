package com.fitness.registration.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    @NotBlank
    private String accountEmail;
    @NotBlank
    private String password;
}
