package com.dkelly205.task_management_system.mapper;

import com.dkelly205.task_management_system.dto.TaskDto;
import com.dkelly205.task_management_system.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskDto mapToTaskDto(Task task){
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.isCompleted())
                .build();
    }

    public Task mapToTask(TaskDto taskDto){
        return Task.builder()
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .completed(taskDto.isCompleted())
                .build();
    }
}
