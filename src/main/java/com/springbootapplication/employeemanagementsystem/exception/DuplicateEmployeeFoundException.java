package com.springbootapplication.employeemanagementsystem.exception;

public class DuplicateEmployeeFoundException extends RuntimeException{
    public DuplicateEmployeeFoundException(String message) {
        super(message);
    }
}
