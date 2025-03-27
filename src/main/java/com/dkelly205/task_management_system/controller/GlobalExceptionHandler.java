package com.dkelly205.task_management_system.controller;

import com.dkelly205.task_management_system.dto.ApiError;
import com.dkelly205.task_management_system.exceptions.TaskApiException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException ex) {
        ApiError error = ApiError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskApiException.class)
    public ResponseEntity<ApiError> handleTaskApiException(TaskApiException ex){
        ApiError error = ApiError.builder()
                .status(ex.getStatus().value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(error, ex.getStatus());
    }


}
