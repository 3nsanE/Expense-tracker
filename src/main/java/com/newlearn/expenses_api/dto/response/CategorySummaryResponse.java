package com.newlearn.expenses_api.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategorySummaryResponse {
    private Long categoryId;
    private String categoryName;
    private BigDecimal totalAmount;
    private Long totalExpenses;
}
