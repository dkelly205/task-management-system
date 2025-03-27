package com.dkelly205.task_management_system.service;

import com.dkelly205.task_management_system.dto.TaskDto;

import java.util.List;

public interface TaskService {

    TaskDto createTask(TaskDto taskDto);

    List<TaskDto> getAllTasks();

    TaskDto getTaskById(Long id);

    void deleteTask(Long id);

    TaskDto updateTask(Long id, TaskDto taskDto);

    TaskDto completeTask(Long id);

    TaskDto incompleteTask(Long id);
}
