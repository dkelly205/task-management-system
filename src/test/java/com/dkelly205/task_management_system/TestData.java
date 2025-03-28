package com.dkelly205.task_management_system;

import com.dkelly205.task_management_system.dto.TaskDto;
import com.dkelly205.task_management_system.entity.Task;

public final class TestData {

    private TestData(){
    }

    public static TaskDto testTaskReact(){
        return TaskDto.builder()
                .title("React")
                .description("Complete React course")
                .completed(false)
                .build();
    }
}
