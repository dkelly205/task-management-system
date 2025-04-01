package com.dkelly205.task_management_system.mapper;

import com.dkelly205.task_management_system.dto.TaskDto;
import com.dkelly205.task_management_system.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {

        TaskMapper INSTANCE = Mappers.getMapper(com.dkelly205.task_management_system.mapper.TaskMapper.class);

        Task mapToTask(TaskDto taskDto);

        TaskDto mapToTaskDto(Task task);

}
