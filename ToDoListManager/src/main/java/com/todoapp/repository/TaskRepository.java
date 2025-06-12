package com.todoapp.repository;

import com.todoapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<Task> findByCompleted(Boolean completed);
    
    List<Task> findByPriority(Task.Priority priority);
    
    @Query("SELECT t FROM Task t WHERE t.title LIKE %:keyword% OR t.description LIKE %:keyword%")
    List<Task> findByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT t FROM Task t ORDER BY " +
           "CASE WHEN t.priority = 'HIGH' THEN 1 " +
           "WHEN t.priority = 'MEDIUM' THEN 2 " +
           "WHEN t.priority = 'LOW' THEN 3 END, " +
           "t.createdAt DESC")
    List<Task> findAllOrderedByPriorityAndDate();
    
    long countByCompleted(Boolean completed);
}