package com.dkelly205.task_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TaskDto {

    private Long id;

    @NotBlank
    @Size(max = 20, message = "Title must be between 1-20 characters")
    private String title;

    @NotBlank
    @Size(max = 100, message = "Description must be between 1-100 characters")
    private String description;

    private boolean completed;
}
