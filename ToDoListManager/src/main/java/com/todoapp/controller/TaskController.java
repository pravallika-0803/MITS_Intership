package com.todoapp.controller;

import com.todoapp.dto.TaskRequest;
import com.todoapp.dto.TaskResponse;
import com.todoapp.model.Task;
import com.todoapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    @Autowired
    TaskService taskService;
    
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) Task.Priority priority,
            @RequestParam(required = false) String search) {
        
        List<TaskResponse> tasks;
        
        if (search != null && !search.trim().isEmpty()) {
            tasks = taskService.searchTasks(search.trim());
        } else if (completed != null && priority != null) {
            // Filter by both status and priority - would need new service method
            tasks = taskService.getAllTasks(); // Simplified for now
        } else if (completed != null) {
            tasks = taskService.getTasksByStatus(completed);
        } else if (priority != null) {
            tasks = taskService.getTasksByPriority(priority);
        } else {
            tasks = taskService.getAllTasks();
        }
        
        return ResponseEntity.ok(tasks);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        TaskResponse task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }
    
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        TaskResponse createdTask = taskService.createTask(taskRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id, 
            @Valid @RequestBody TaskRequest taskRequest) {
        TaskResponse updatedTask = taskService.updateTask(id, taskRequest);
        return ResponseEntity.ok(updatedTask);
    }
    
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<TaskResponse> toggleTaskCompletion(@PathVariable Long id) {
        TaskResponse updatedTask = taskService.toggleTaskCompletion(id);
        return ResponseEntity.ok(updatedTask);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/statistics")
    public ResponseEntity<TaskService.TaskStatistics> getTaskStatistics() {
        TaskService.TaskStatistics stats = taskService.getTaskStatistics();
        return ResponseEntity.ok(stats);
    }
}