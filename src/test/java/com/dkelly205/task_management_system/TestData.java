package com.dkelly205.task_management_system;

import com.dkelly205.task_management_system.dto.TaskDto;
import com.dkelly205.task_management_system.entity.Task;

import java.util.Arrays;
import java.util.List;

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

    public static List<TaskDto> aListOfTaskDtos() {
        return Arrays.asList(
                new TaskDto(1L, "React", "Description for React", false),
                new TaskDto(2L, "HTML", "Description for HTML", true),
                new TaskDto(3L, "CSS", "Description for CSS", false),
                new TaskDto(4L, "AWS", "Description for AWS", false),
                new TaskDto(5L, "Java", "Description for Java", false)
        );
    }
}
