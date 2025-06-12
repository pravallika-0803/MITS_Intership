package com.todoapp.service;

import com.todoapp.dto.TaskRequest;
import com.todoapp.dto.TaskResponse;
import com.todoapp.model.Task;
import com.todoapp.model.Task.Priority;
import com.todoapp.repository.TaskRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAllOrderedByPriorityAndDate()
                .stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        return Objects.nonNull(task) ? new TaskResponse(task) : null;
    }

    public TaskResponse createTask(TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setPriority(taskRequest.getPriority());

        Task savedTask = taskRepository.save(task);
        return new TaskResponse(savedTask);
    }

    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id)
                .orElse(null);

        if (Objects.nonNull(task)) {
            task.setTitle(taskRequest.getTitle());
            task.setDescription(taskRequest.getDescription());
            task.setPriority(taskRequest.getPriority());
            Task updatedTask = taskRepository.save(task);
            return new TaskResponse(updatedTask);
        }
        return null;
    }

    public TaskResponse toggleTaskCompletion(Long id) {
        Task task = taskRepository.findById(id)
                .orElse(null);

        if (Objects.nonNull(task)) {
            task.setCompleted(!task.getCompleted());
            Task updatedTask = taskRepository.save(task);
            return new TaskResponse(updatedTask);
        }
        return null;
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            return;
        }
        taskRepository.deleteById(id);
    }

    public List<TaskResponse> getTasksByStatus(Boolean completed) {
        return taskRepository.findByCompleted(completed)
                .stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> getTasksByPriority(Priority priority) {
        return taskRepository.findByPriority(priority)
                .stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> searchTasks(String keyword) {
        return taskRepository.findByKeyword(keyword)
                .stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    public TaskStatistics getTaskStatistics() {
        long totalTasks = taskRepository.count();
        long completedTasks = taskRepository.countByCompleted(true);
        long pendingTasks = taskRepository.countByCompleted(false);

        return new TaskStatistics(totalTasks, completedTasks, pendingTasks);
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class TaskStatistics {
        // Getters
        private final long totalTasks;
        private final long completedTasks;
        private final long pendingTasks;
    }
}