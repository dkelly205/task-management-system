package com.dkelly205.task_management_system.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private boolean completed;
}
