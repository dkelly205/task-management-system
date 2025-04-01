package com.dkelly205.task_management_system.controller;

import com.dkelly205.task_management_system.dto.TaskDto;
import com.dkelly205.task_management_system.entity.Task;
import com.dkelly205.task_management_system.repository.TaskRepository;
import com.dkelly205.task_management_system.service.TaskService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.dkelly205.task_management_system.TestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class TaskControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    private String token;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() throws Exception {
       ResultActions result = mockMvc.perform(post("/api/auth/login")
               .contentType("application/json")
               .content("{ \"usernameOrEmail\": \"admin\", \"password\":\"admin\"}"));

       String responseBody = result.andReturn().getResponse().getContentAsString();
       JsonNode jsonResponse = objectMapper.readTree(responseBody);
       token = jsonResponse.get("accessToken").asText();

       taskRepository.deleteAll();

    }

    @Test
    public void testThatTaskIsCreated() throws Exception {

        final TaskDto task = testTaskDto();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String taskJson = objectMapper.writeValueAsString(task);
        mockMvc.perform(post("/api/v1/tasks")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.completed").value(task.isCompleted()));

    }

    @Test
    public void testThatTaskCanBeRetrieved() throws Exception {

        final TaskDto task = testTaskDto();
        TaskDto savedTask = taskService.createTask(task);

        mockMvc.perform(get("/api/v1/tasks/" + savedTask.getId())
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.completed").value(task.isCompleted()));


    }

    @Test
    public void testThatHttpStatus200IsReturnedWhenRetrievingTasks() throws Exception {

        List<Task> tasks = aListOfTasks();
        tasks.forEach(task -> taskRepository.save(task));

        mockMvc.perform(get("/api/v1/tasks")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.length()").value(5))
                .andExpect(jsonPath("$.content[0].title").value(tasks.get(0).getTitle()))
                .andExpect(jsonPath("$.content[1].title").value(tasks.get(1).getTitle()))
                .andExpect(jsonPath("$.content[2].title").value(tasks.get(2).getTitle()))
                .andExpect(jsonPath("$.content[3].title").value(tasks.get(3).getTitle()))
                .andExpect(jsonPath("$.content[4].title").value(tasks.get(4).getTitle()))
                .andExpect(jsonPath("$.totalElements").value(5)) // Total number of tasks in the database
                .andExpect(jsonPath("$.totalPages").value(1));

    }

//    @Test
//    public void testThatTasksAreSortedByTitle() throws Exception {
//
//        List<TaskDto> taskDtos = aListOfTaskDtos();
//        taskDtos.forEach(taskDto -> taskService.createTask(taskDto));
//
//
//
//        mockMvc.perform(get("/api/v1/tasks?pageSize=5&offset=0&sortBy=title")
//                        .header("Authorization", "Bearer " + token))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//
//    }

    @Test
    public void testThatHttp204IsReturnedWhenTaskIsDeleted() throws Exception {
        final TaskDto task = testTaskDto();
        TaskDto savedTask = taskService.createTask(task);
        mockMvc.perform(delete("/api/v1/tasks/" + savedTask.getId())
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatHttp200IsReturnedWhenTaskIsCompleted() throws Exception {
        final TaskDto task = testTaskDto();
        TaskDto savedTask = taskService.createTask(task);
        mockMvc.perform(patch("/api/v1/tasks/" + savedTask.getId()  + "/complete")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.completed").value(!task.isCompleted()));
    }


    @Test
    public void testThatHttp200IsReturnedWhenTaskChangedToIncomplete() throws Exception {
        final TaskDto task = testTaskDto();
        task.setCompleted(true);
        TaskDto savedTask = taskService.createTask(task);
        mockMvc.perform(patch("/api/v1/tasks/" + savedTask.getId()  + "/incomplete")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.completed").value(!task.isCompleted()));
    }




}
