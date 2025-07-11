package com.example.ExpenseTrackerApplication.controller;

import com.example.ExpenseTrackerApplication.service.ExpenseService;
import com.example.ExpenseTrackerApplication.dto.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:3000")
public class ExpenseController {

    private final ExpenseService svc;

    public ExpenseController(ExpenseService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ExpenseResponse add(@Valid @RequestBody ExpenseRequest r) {
        return svc.addExpense(r);
    }

    @GetMapping("/total")
    public BigDecimal total() {
        return svc.getTotal();
    }

    @GetMapping("/categories")
    public List<CategorySummary> byCategory() {
        return svc.byCategory();
    }

    @GetMapping("/categories/highest")
    public CategorySummary highest() {
        return svc.highestCategory();
    }

    @GetMapping("/categories/lowest")
    public CategorySummary lowest() {
        return svc.lowestCategory();
    }

    @GetMapping("/trend")
    public List<TrendPoint> trend(@RequestParam(defaultValue="DAILY") String period) {
        Period p;
        switch (period.toUpperCase()) {
            case "DAILY":   p = Period.ofDays(1);   break;
            case "WEEKLY":  p = Period.ofWeeks(1);  break;
            case "MONTHLY": p = Period.ofMonths(1); break;
            default:
                throw new IllegalArgumentException(
                        "Invalid period '" + period + "'.  Use DAILY, WEEKLY or MONTHLY."
                );
        }
        return svc.trend(p);
    }
}
