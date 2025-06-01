package com.mykola.railroad.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {
    public @NotBlank String email;
    public @NotBlank String password;
}
