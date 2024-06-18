package com.example.Ex1.web;

import com.example.Ex1.ErrorHandlers.ErrorResponse;
import com.example.Ex1.ErrorHandlers.TaskNotFoundException;
import com.example.Ex1.domain.Task;
import com.example.Ex1.ErrorHandlers.TaskAlreadyExistsException;
import com.example.Ex1.ErrorHandlers.TaskDoesNotExistsException;
import com.example.Ex1.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/TaskAPI")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return  taskService.getAllTasks();
    }

    @PostMapping
    public ResponseEntity<?> createTask(@Valid @RequestBody Task newTask , BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return ResponseEntity.badRequest().body(errors);
        }try {

            Task task = taskService.createTask(newTask);

            return ResponseEntity.status(HttpStatus.CREATED).body(task);
        } catch (TaskAlreadyExistsException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable int id){
        try {
           taskService.deleteTask(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        catch (TaskDoesNotExistsException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable int id) {
        try {
            Task task = taskService.getTaskById(id);
            return ResponseEntity.ok(task);
        } catch (TaskDoesNotExistsException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updatePutTask(@PathVariable int id, @RequestBody Task updatedTask) {
        try {
            Task task = taskService.updateTask(id, updatedTask);
            return ResponseEntity.ok(task);
        } catch (TaskNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePatchTask(@PathVariable int id, @RequestBody Task updatedTask) {
        try {
            Task task = taskService.patchTask(id, updatedTask);
            return ResponseEntity.ok(task);
        } catch (TaskNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/completed/{completed}")
    public List<Task> getTasksByCompletionStatus(@PathVariable boolean completed) {
        return taskService.getTasksByCompletionStatus(completed);
    }

    @GetMapping("/title/{title}")
    public List<Task> getTasksByTitle(@PathVariable String title) {
        return taskService.getTasksByTitle(title);
    }

    @GetMapping("/description/{keyword}")
    public List<Task> getTasksByDescriptionContaining(@PathVariable String keyword) {
        return taskService.getTasksByDescriptionContaining(keyword);
    }

    @GetMapping("/id-range/{startId}/{endId}")
    public List<Task> getTasksByIdRange(@PathVariable int startId, @PathVariable int endId) {
        return taskService.getTasksByIdRange(startId, endId);
    }

    @GetMapping("/keyword/{keyword}")
    public List<Task> getTasksByTitleOrDescription(@PathVariable String keyword) {
        return taskService.getTasksByTitleOrDescription(keyword);
    }


}
