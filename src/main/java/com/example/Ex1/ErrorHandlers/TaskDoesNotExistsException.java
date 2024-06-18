package com.example.Ex1.ErrorHandlers;

public class TaskDoesNotExistsException extends RuntimeException {

    public TaskDoesNotExistsException(String message) {
        super(message);
    }
}
