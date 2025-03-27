package com.dkelly205.task_management_system.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterDto {

    private String email;
    private String password;
    private String username;
}
