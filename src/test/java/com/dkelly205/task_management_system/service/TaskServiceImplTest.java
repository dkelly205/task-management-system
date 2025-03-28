package com.dkelly205.task_management_system.service;

import com.dkelly205.task_management_system.dto.TaskDto;
import com.dkelly205.task_management_system.entity.Task;
import com.dkelly205.task_management_system.mapper.TaskMapper;
import com.dkelly205.task_management_system.repository.TaskRepository;
import com.dkelly205.task_management_system.service.impl.TaskServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.dkelly205.task_management_system.TestData.testTask;
import static com.dkelly205.task_management_system.TestData.testTaskDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl underTest;

    @Test
    public void testThatTaskIsSaved() {
        final TaskDto taskDto = testTaskDto();
        final Task task = testTask();

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(taskRepository.save(eq(task))).thenReturn(task);
        when(taskMapper.mapToTaskDto(eq(task))).thenReturn(taskDto);

        final TaskDto result = underTest.createTask(taskDto);

        verify(taskMapper).mapToTask(eq(taskDto));
        verify(taskRepository).save(eq(task));
        verify(taskMapper).mapToTaskDto(eq(task));
        assertEquals(taskDto, result);


    }

    @Test
    public void testDeleteTaskDeletesTaskById(){
        final Long id = 1L;
        underTest.deleteTask(id);
        verify(taskRepository).deleteById(id);

    }

    @Test
    public void testUpdateTaskUpdatesTask(){
        final Long id = 1L;
        final TaskDto updatedTaskDto = testTaskDto();
        updatedTaskDto.setTitle("Updated title");
        final Task existingTask = testTask();
        when(taskRepository.findById(id)).thenReturn(Optional.of(existingTask));
        when(taskMapper.mapToTaskDto(eq(existingTask))).thenReturn(updatedTaskDto);
        when(taskRepository.save(eq(existingTask))).thenReturn(existingTask);


        TaskDto result = underTest.updateTask(id, updatedTaskDto);
        verify(taskRepository).findById(id);
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(taskMapper).mapToTaskDto(eq(existingTask));

        assertEquals(updatedTaskDto, result);

    }

    @Test
    public void getTaskByIdThrowsEntityNotFoundExceptionWhenTaskDoesNotExist() {

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            underTest.getTaskById(100L);
        });

        String expectedMessage = "Task not found with id: 100";

        assertTrue(exception.getMessage().contains(expectedMessage));
    }




}
