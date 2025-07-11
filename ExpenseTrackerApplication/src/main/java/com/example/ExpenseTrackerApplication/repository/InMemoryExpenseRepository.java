package com.example.ExpenseTrackerApplication.repository;

import com.example.ExpenseTrackerApplication.model.Expense;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ConcurrentHashMap;
import java.util.*;

@Repository
public class InMemoryExpenseRepository implements ExpenseRepository{
    private final Map<UUID, Expense> store = new ConcurrentHashMap<>();

    @Override
    public Expense save(Expense e) {
        store.put(e.getId(), e);
        return e;
    }

    @Override
    public List<Expense> findAll() {
        return new ArrayList<>(store.values());
    }
}
