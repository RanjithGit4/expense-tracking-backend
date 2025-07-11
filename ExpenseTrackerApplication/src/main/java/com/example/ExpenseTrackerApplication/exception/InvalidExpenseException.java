package com.example.ExpenseTrackerApplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidExpenseException extends RuntimeException{
    public InvalidExpenseException(String msg) {
        super(msg);
    }
}
