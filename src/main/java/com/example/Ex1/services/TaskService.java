package com.example.Ex1.services;

import com.example.Ex1.ErrorHandlers.TaskAlreadyExistsException;
import com.example.Ex1.ErrorHandlers.TaskDoesNotExistsException;
import com.example.Ex1.ErrorHandlers.TaskNotFoundException;
import com.example.Ex1.domain.Task;
import com.example.Ex1.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task newTask) {
        if (taskRepository.existsByTitle(newTask.getTitle())) {
            throw new TaskAlreadyExistsException("Task with title '" + newTask.getTitle() + "' already exists.");
        }
        return taskRepository.save(newTask);
    }

    public void deleteTask(int id){
        if (!taskRepository.existsById(id)) {
            throw new TaskDoesNotExistsException("Task with id '" + id + "' does not exists.");
        }
        taskRepository.deleteById(id);
    }

    public Task updateTask(int id, Task updatedTask) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setCompleted(updatedTask.isCompleted());
            return taskRepository.save(task);
        } else {
            throw new TaskNotFoundException("Task with id " + id + " not found.");
        }
    }
    public Task patchTask(int id, Task updatedTask) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            if (updatedTask.getTitle() != null) {
                task.setTitle(updatedTask.getTitle());
            }
            if (updatedTask.getDescription() != null) {
                task.setDescription(updatedTask.getDescription());
            }
            if (updatedTask.isCompleted() != task.isCompleted()) {
                task.setCompleted(updatedTask.isCompleted());
            }
            return taskRepository.save(task);
        } else {
            throw new TaskNotFoundException("Task with id " + id + " not found.");
        }
    }
    public Task getTaskById(int id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskDoesNotExistsException("Task not found with id " + id));
    }

    public List<Task> getTasksByCompletionStatus(boolean completed) {
        return taskRepository.findByCompleted(completed);
    }

    public List<Task> getTasksByTitle(String title) {
        return taskRepository.findByTitle(title);
    }
    public List<Task> getTasksByDescriptionContaining(String keyword) {
        return taskRepository.findByDescriptionContaining(keyword);
    }


    public List<Task> getTasksByIdRange(int startId, int endId) {
        return taskRepository.findByIdBetween(startId, endId);
    }

    public List<Task> getTasksByTitleOrDescription(String keyword) {
        return taskRepository.findByTitleOrDescriptionContaining(keyword);
    }


}
