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

/**
 * REST Controller: Handles HTTP requests for Task API.
 */
@RestController
@RequestMapping("/TaskAPI")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * GET /TaskAPI - Get all tasks
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return  taskService.getAllTasks();
    }

    /**
     * POST /TaskAPI - Create a new task
     * Validation: Uses @Valid for input validation
     * and BindingResult for error handling in createTask method.
     *
     * Exception Handling: Catches and returns appropriate HTTP status codes
     * and error messages for exceptions thrown by service layer.
     */

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
    /**
     * DELETE /TaskAPI/{id} - Delete task by id
     */
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

    /**
     * GET /TaskAPI/{id} - Get task by id
     */
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

    /**
     * PUT /TaskAPI/{id} - Update task by id (replace)
     */
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
    /**
     * PATCH /TaskAPI/{id} - Update task by id (partial update)
     */
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
    /**
     * GET /TaskAPI/completed/{completed} - Get tasks by completion status
     */
    @GetMapping("/completed/{completed}")
    public List<Task> getTasksByCompletionStatus(@PathVariable boolean completed) {
        return taskService.getTasksByCompletionStatus(completed);
    }
    /**
     * GET /TaskAPI/title/{title} - Get tasks by title
     */
    @GetMapping("/title/{title}")
    public List<Task> getTasksByTitle(@PathVariable String title) {
        return taskService.getTasksByTitle(title);
    }

    /**
     * GET /TaskAPI/description/{keyword} - Get tasks by description containing keyword
     */
    @GetMapping("/description/{keyword}")
    public List<Task> getTasksByDescriptionContaining(@PathVariable String keyword) {
        return taskService.getTasksByDescriptionContaining(keyword);
    }
    /**
     * GET /TaskAPI/id-range/{startId}/{endId} - Get tasks by id range
     */
    @GetMapping("/id-range/{startId}/{endId}")
    public List<Task> getTasksByIdRange(@PathVariable int startId, @PathVariable int endId) {
        return taskService.getTasksByIdRange(startId, endId);
    }
    /**
     * GET /TaskAPI/keyword/{keyword} - Get tasks by title or description containing keyword
     */
    @GetMapping("/keyword/{keyword}")
    public List<Task> getTasksByTitleOrDescription(@PathVariable String keyword) {
        return taskService.getTasksByTitleOrDescription(keyword);
    }


}
