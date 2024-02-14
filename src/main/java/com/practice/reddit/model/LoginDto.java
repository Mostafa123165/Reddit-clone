package com.practice.reddit.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class LoginDto {

    @NotBlank(message = "username is required")
    private String username ;

    @NotBlank(message = "password is required")
    private String password ;
}
