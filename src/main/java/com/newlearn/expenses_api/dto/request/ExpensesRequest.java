package com.newlearn.expenses_api.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpensesRequest {
    @NotBlank(message = "Title cannot be empty")
    @Size(max=100,message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 200,message = "Description cannot exceed 200 characters")
    private String description;

    @NotNull(message = "Amount cannot be empty")
    @DecimalMin(value="0.01",message = "Amount must be more than 0")
    @Digits(integer = 10,fraction = 2,message = "Amount can have 10 digits and 2 decimal places")
    private BigDecimal amount;

    @NotNull(message = "Expense date is required")
    @PastOrPresent(message = "Expense date cannot be in future")
    private LocalDate expensesDate;

    @NotNull(message = "Category Id is required")
    @Positive(message = "Category Id must be positive")
    private Long categoryId;
}
