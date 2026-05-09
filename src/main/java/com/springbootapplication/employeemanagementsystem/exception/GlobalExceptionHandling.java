package com.springbootapplication.employeemanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandling {
    @ExceptionHandler(DuplicateEmployeeFoundException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmployee(DuplicateEmployeeFoundException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(409, e.getMessage(), LocalDateTime.now()));
    }
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeNotFound(EmployeeNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, e.getMessage(), LocalDateTime.now()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgsInvalid(MethodArgumentNotValidException e){
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error-> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", errors);
        response.put("timeStamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleMethodTypeMistMatch(MethodArgumentTypeMismatchException e){
          Map<String, Object> response = new HashMap<>();
          String paramName = e.getName();
          Object invalidValue = e.getValue();
          response.put("status", HttpStatus.BAD_REQUEST.value());
          response.put("message", "Invalid value '"+ invalidValue+"' for parameter '"+ paramName);
          response.put("timeStamp", LocalDateTime.now());
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Unexpected Error Occurred", LocalDateTime.now()));
    }
}