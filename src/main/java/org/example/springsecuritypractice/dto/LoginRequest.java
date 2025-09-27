package org.example.springsecuritypractice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotEmpty(message = "email should not be empty")
    @Email(message = "Please enter valid email")
    private String email;

    @Size(min = 8, message = "Password must be atleast 8 chars long")
    @NotEmpty(message = "Password should not be empty")
    private String password;

}
