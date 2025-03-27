package com.dkelly205.task_management_system.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {

    private int status;
    private String message;
    private List<FieldError> errors;



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class FieldError {
        private String field;
        private String message;
    }
}
