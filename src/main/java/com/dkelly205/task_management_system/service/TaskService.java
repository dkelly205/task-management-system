package com.dkelly205.task_management_system.service;

import com.dkelly205.task_management_system.dto.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface TaskService {

    TaskDto createTask(TaskDto taskDto);

    Page<TaskDto> findAll(PageRequest pageRequest);

    TaskDto getTaskById(Long id);

    void deleteTask(Long id);

    TaskDto updateTask(Long id, TaskDto taskDto);

    TaskDto completeTask(Long id);

    TaskDto incompleteTask(Long id);
}
