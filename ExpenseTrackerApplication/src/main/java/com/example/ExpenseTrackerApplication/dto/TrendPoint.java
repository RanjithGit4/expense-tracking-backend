package com.example.ExpenseTrackerApplication.dto;
import java.math.BigDecimal;
import java.time.LocalDate;


public class TrendPoint {
    private LocalDate date;
    private BigDecimal total;

    public TrendPoint(LocalDate date, BigDecimal total) {
        this.date = date;
        this.total = total;
    }

    public BigDecimal getTotal(){
        return total;
    }
    public void setTotal(BigDecimal t){
        total=t;
    }

    public LocalDate getDate(){
        return date;
    }
    public void setDate(LocalDate lDate){
        date=lDate;
    }
}
