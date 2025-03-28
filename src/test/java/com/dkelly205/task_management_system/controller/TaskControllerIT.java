package com.dkelly205.task_management_system.controller;

import com.dkelly205.task_management_system.dto.TaskDto;
import com.dkelly205.task_management_system.entity.Task;
import com.dkelly205.task_management_system.service.TaskService;
import com.dkelly205.task_management_system.service.impl.TaskServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
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

import static com.dkelly205.task_management_system.TestData.testTaskReact;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class TaskControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService;

    private String token;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void getBearerToken() throws Exception {
       ResultActions result = mockMvc.perform(post("/api/auth/login")
               .contentType("application/json")
               .content("{ \"usernameOrEmail\": \"admin\", \"password\":\"admin\"}"));

       String responseBody = result.andReturn().getResponse().getContentAsString();
       JsonNode jsonResponse = objectMapper.readTree(responseBody);
       token = jsonResponse.get("accessToken").asText();

    }

    @Test
    public void testThatTaskIsCreated() throws Exception {

        final TaskDto task = testTaskReact();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String taskJson = objectMapper.writeValueAsString(task);
        mockMvc.perform(post("/api/v1/tasks")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(task.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(task.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.completed").value(task.isCompleted()));

    }

    @Test
    public void testThatTaskCanBeRetrieved() throws Exception {

        final TaskDto task = testTaskReact();
        TaskDto savedTask = taskService.createTask(task);

        mockMvc.perform(get("/api/v1/tasks/" + savedTask.getId())
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(task.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(task.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.completed").value(task.isCompleted()));


    }

    @Test
    public void testThatHttp204IsReturnedWhenTaskIsDeleted() throws Exception {
        final TaskDto task = testTaskReact();
        TaskDto savedTask = taskService.createTask(task);
        mockMvc.perform(delete("/api/v1/tasks/" + savedTask.getId())
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatHttp200IsReturnedWhenTaskIsCompleted() throws Exception {
        final TaskDto task = testTaskReact();
        TaskDto savedTask = taskService.createTask(task);
        mockMvc.perform(patch("/api/v1/tasks/" + savedTask.getId()  + "/complete")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(task.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(task.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.completed").value(!task.isCompleted()));
    }


    @Test
    public void testThatHttp200IsReturnedWhenTaskChangedToIncomplete() throws Exception {
        final TaskDto task = testTaskReact();
        task.setCompleted(true);
        TaskDto savedTask = taskService.createTask(task);
        mockMvc.perform(patch("/api/v1/tasks/" + savedTask.getId()  + "/incomplete")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(task.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(task.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.completed").value(!task.isCompleted()));
    }




}
