package com.example.ExpenseTrackerApplication.dto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ExpenseResponse {
    private UUID id;
    private String category;
    private BigDecimal amount;
    private LocalDate date;

    public ExpenseResponse(UUID id, String category, BigDecimal amount, LocalDate date) {
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public UUID getId(){
        return id;
    }

    public String getCategory(){
        return category;
    }
    public BigDecimal getAmount(){
        return amount;
    }
    public LocalDate getDate(){
        return date;
    }
    public void setUUID(UUID uId){
        id=uId;
    }
    public void setCategory(String cat){
        category=cat;
    }
    public void setAmount(BigDecimal amt){
        amount=amt;
    }
    public void setDate(LocalDate lDate){
        date=lDate;
    }
}
