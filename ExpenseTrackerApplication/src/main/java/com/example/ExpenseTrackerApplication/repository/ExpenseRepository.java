package com.example.ExpenseTrackerApplication.repository;

import com.example.ExpenseTrackerApplication.model.Expense;

import java.util.List;


public interface ExpenseRepository {
    Expense save(Expense e);
    List<Expense> findAll();
}
