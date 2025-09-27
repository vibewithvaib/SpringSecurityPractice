package org.example.springsecuritypractice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequest {
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Please provide valid email address")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8,message = "Password should be atleast")
    private String password;
}
