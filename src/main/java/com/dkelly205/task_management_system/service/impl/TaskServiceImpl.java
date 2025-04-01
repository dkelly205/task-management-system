package com.dkelly205.task_management_system.service.impl;

import com.dkelly205.task_management_system.dto.TaskDto;
import com.dkelly205.task_management_system.entity.Task;
import com.dkelly205.task_management_system.mapper.TaskMapper;
import com.dkelly205.task_management_system.repository.TaskRepository;
import com.dkelly205.task_management_system.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;


    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        Task savedTask = taskRepository.save(task);
        return taskMapper.mapToTaskDto(savedTask);
    }

    @Override
    public Page<TaskDto> findAll(PageRequest pageRequest) {
        return taskRepository.findAll(pageRequest)
                .map(taskMapper::mapToTaskDto);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::mapToTaskDto)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());
        Task updatedTask = taskRepository.save(task);
        return taskMapper.mapToTaskDto(updatedTask);

    }

    @Override
    public TaskDto completeTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

        task.setCompleted(true);
        Task updatedTask = taskRepository.save(task);
        return taskMapper.mapToTaskDto(updatedTask);
    }

    @Override
    public TaskDto incompleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

        task.setCompleted(false);
        Task updatedTask = taskRepository.save(task);
        return taskMapper.mapToTaskDto(updatedTask);
    }
}
