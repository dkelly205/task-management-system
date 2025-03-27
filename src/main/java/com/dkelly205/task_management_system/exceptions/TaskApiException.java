package com.dkelly205.task_management_system.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class TaskApiException extends RuntimeException{

    private HttpStatus status;
    private String message;
}
