package com.example.ExpenseTrackerApplication.dto;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ExpenseRequest {
    @NotBlank(message="Category is required")
    private String category;

    @NotNull @DecimalMin(value="0.01", message="Must be > 0")
    private BigDecimal amount;

    @NotNull @PastOrPresent(message="Date cannot be in the future")
    private LocalDate date;

    public String getCategory(){
        return category;
    }
    public BigDecimal getAmount(){
        return amount;
    }
    public LocalDate getDate(){
        return date;
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
