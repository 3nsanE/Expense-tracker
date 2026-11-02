package com.newlearn.expenses_api.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetRequest {
    @NotNull(message = "Monthly limit is required")
    @DecimalMin(value = "0.01",message = "Monthly limit must be more than 0")
    @Digits(integer = 10,fraction = 2,message = "Monthly limit cannot have more than 10 integer digits and 2 decimal places")
    private BigDecimal monthlyLimit;

    @NotNull(message = "Month ie required")
    @Min(value = 1,message = "Month must be between 1-12")
    @Max(value = 12,message = "Month must be between 1-12")
    private Integer month;

    @NotNull(message = "Year is required")
    private Integer year;

    @AssertTrue(message = "Year cannot be from the past")
    public boolean isYearValid() {
        return year != null || year >= java.time.Year.now().getValue();
    }
}
