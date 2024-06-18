package com.example.Ex1.repositories;

import com.example.Ex1.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    boolean existsByTitle(String title);
    boolean existsById(int id);

    @Query("SELECT t FROM Task t WHERE t.title LIKE %:keyword% OR t.description LIKE %:keyword%")
    List<Task> findByTitleOrDescriptionContaining(@Param("keyword") String keyword);

    List<Task> findByIdBetween(int startId, int endId);

    List<Task> findByCompleted(boolean completed);

    List<Task> findByTitle(String title);

    List<Task> findByDescriptionContaining(String keyword);
}
