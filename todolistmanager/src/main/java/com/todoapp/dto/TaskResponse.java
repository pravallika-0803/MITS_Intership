package com.todoapp.dto;

import com.todoapp.model.Task;
import com.todoapp.model.Task.Priority;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private Boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.priority = task.getPriority();
        this.completed = task.getCompleted();
        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
        this.completedAt = task.getCompletedAt();
    }
}