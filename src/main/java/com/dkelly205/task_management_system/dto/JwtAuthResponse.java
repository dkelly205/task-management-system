package com.dkelly205.task_management_system.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtAuthResponse {

    private String tokenType;
    private String accessToken;
    private String role;
}
