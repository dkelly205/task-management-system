package com.dkelly205.task_management_system.exceptions;

public class UniqueConstraintViolationException extends RuntimeException {

    public UniqueConstraintViolationException(String message){
        super(message);
    }
}
