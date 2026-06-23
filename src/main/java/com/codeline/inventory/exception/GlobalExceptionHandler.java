package com.codeline.inventory.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {
        String message = ex.getMessage();
        if (message != null && message.contains("stock")) {
            return ResponseEntity.status(409).body(Map.of("error", message));
        }
        if (message != null && message.contains("not found")) {
            return ResponseEntity.status(404).body(Map.of("error", message));
        }
        return ResponseEntity.status(400).body(Map.of("error", message));
    }
}