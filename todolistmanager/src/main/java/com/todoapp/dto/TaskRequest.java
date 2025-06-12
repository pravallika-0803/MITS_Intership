package com.todoapp.dto;

import com.todoapp.model.Task;
import com.todoapp.model.Task.Priority;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class TaskRequest {
    
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
    
    private Priority priority = Priority.MEDIUM;

    TaskRequest() {}

    public TaskRequest(String title, String description, Task.Priority priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }
}