package com.dkelly205.task_management_system;

import com.dkelly205.task_management_system.dto.TaskDto;
import com.dkelly205.task_management_system.entity.Task;

public final class TestData {

    private static final String TASK_TITLE = "React";
    private static final String TASK_DESCRIPTION = "Complete scrimba course";

    private TestData(){
    }

    public static TaskDto testTaskDto(){
        return TaskDto.builder()
                .title(TASK_TITLE)
                .description(TASK_DESCRIPTION)
                .completed(false)
                .build();
    }

    public static Task testTask(){
        return Task.builder()
                .title(TASK_TITLE)
                .description(TASK_DESCRIPTION)
                .completed(false)
                .build();
    }
}
