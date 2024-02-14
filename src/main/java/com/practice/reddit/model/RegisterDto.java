package com.practice.reddit.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotBlank(message = "The user name is not valid")
    private String userName;

    @Email
    @NotBlank(message = "The user email is not valid")
    private String email ;

    @Size(min = 6 , max = 400 , message = "The password is not valid")
    private String password;
}
