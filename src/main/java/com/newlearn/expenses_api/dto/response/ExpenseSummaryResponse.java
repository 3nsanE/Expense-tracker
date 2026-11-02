package com.newlearn.expenses_api.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseSummaryResponse {
    private BigDecimal totalExpenses;
    private Long totalTransactions;
    private BigDecimal averageExpenses;
    private BigDecimal highestExpenses;
    private BigDecimal lowestExpenses;
}
