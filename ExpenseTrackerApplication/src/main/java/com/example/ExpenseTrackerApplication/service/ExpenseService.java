package com.example.ExpenseTrackerApplication.service;

import com.example.ExpenseTrackerApplication.dto.*;
import com.example.ExpenseTrackerApplication.exception.InvalidExpenseException;
import com.example.ExpenseTrackerApplication.model.Expense;
import com.example.ExpenseTrackerApplication.repository.ExpenseRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.*;

@Service
public class ExpenseService {
    private final ExpenseRepository repo;
    public ExpenseService(ExpenseRepository repo) { this.repo = repo; }

    public ExpenseResponse addExpense(ExpenseRequest r) {
        if (r.getAmount().compareTo(BigDecimal.ZERO) <= 0)
            throw new InvalidExpenseException("Amount must be > 0");
        Expense e = new Expense(UUID.randomUUID(), r.getCategory(), r.getAmount(), r.getDate());
        return map(repo.save(e));
    }

    public BigDecimal getTotal() {
        return repo.findAll().stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<CategorySummary> byCategory() {
        return repo.findAll().stream()
                .collect(Collectors.groupingBy(Expense::getCategory,
                        Collectors.mapping(Expense::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))))
                .entrySet().stream()
                .map(e -> new CategorySummary(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    public CategorySummary highestCategory() {
        return byCategory().stream()
                .max(Comparator.comparing(CategorySummary::getTotal))
                .orElse(new CategorySummary("N/A", BigDecimal.ZERO));
    }

    public CategorySummary lowestCategory() {
        return byCategory().stream()
                .min(Comparator.comparing(CategorySummary::getTotal))
                .orElse(new CategorySummary("N/A", BigDecimal.ZERO));
    }

    public List<TrendPoint> trend(Period period) {
        List<Expense> all = repo.findAll();

        // DAILY: one point per day (summing multiple per day)
        if (period.equals(Period.ofDays(1))) {
            return all.stream()
                    .collect(Collectors.groupingBy(Expense::getDate,
                            Collectors.mapping(Expense::getAmount,
                                    Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))))
                    .entrySet().stream()
                    .map(e -> new TrendPoint(e.getKey(), e.getValue()))
                    .sorted(Comparator.comparing(TrendPoint::getDate))
                    .collect(Collectors.toList());
        }

        // WEEKLY: one point per ISO-week start (summing)
        if (period.equals(Period.ofWeeks(1))) {
            return all.stream()
                    .collect(Collectors.groupingBy(e ->
                                    e.getDate().with(WeekFields.ISO.dayOfWeek(), 1),
                            Collectors.mapping(Expense::getAmount,
                                    Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))))
                    .entrySet().stream()
                    .map(e -> new TrendPoint(e.getKey(), e.getValue()))
                    .sorted(Comparator.comparing(TrendPoint::getDate))
                    .collect(Collectors.toList());
        }

        // MONTHLY: one point **per expense** in the current month
        if (period.equals(Period.ofMonths(1))) {
            YearMonth current = YearMonth.now();
            return all.stream()
                    // keep only this month’s entries
                    .filter(e -> YearMonth.from(e.getDate()).equals(current))
                    // map each expense to its own TrendPoint
                    .map(e -> new TrendPoint(e.getDate(), e.getAmount()))
                    .sorted(Comparator.comparing(TrendPoint::getDate))
                    .collect(Collectors.toList());
        }

        throw new IllegalArgumentException("Unsupported period: " + period);
    }

    private ExpenseResponse map(Expense e) {
        return new ExpenseResponse(e.getId(), e.getCategory(), e.getAmount(), e.getDate());
    }
}
