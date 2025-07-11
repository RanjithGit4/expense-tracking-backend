package com.example.ExpenseTrackerApplication.dto;
import java.math.BigDecimal;

public class CategorySummary {
    private String category;
    private BigDecimal total;

    public CategorySummary(String category, BigDecimal total) {
        this.category = category;
        this.total = total;
    }

    public String getCategory(){
        return category;
    }
    public void setCategory(String cat){
        category=cat;
    }

    public BigDecimal getTotal(){
        return total;
    }
    public void setTotal(BigDecimal t){
        total=t;
    }
}
