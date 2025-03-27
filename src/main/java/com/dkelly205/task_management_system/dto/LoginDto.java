package com.dkelly205.task_management_system.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginDto {

    private String usernameOrEmail;
    private String password;
}
