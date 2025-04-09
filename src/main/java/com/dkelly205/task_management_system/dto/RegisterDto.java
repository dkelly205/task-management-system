package com.dkelly205.task_management_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterDto {

    @NotBlank
    @Email(message = "Email must be valid")
    @Size(max = 50, message = "Email must be no more than 50 characters long")
    private String email;

    @NotBlank
    @Size(min = 8, max = 20, message = "Password must be between 8-20 characters")
    private String password;

    @NotBlank
    @Size(max=50, message = "Username must be no more than 50 characters long")
    private String username;
}
