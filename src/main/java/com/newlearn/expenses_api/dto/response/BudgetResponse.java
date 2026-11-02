package com.newlearn.expenses_api.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetResponse {
    private Long id;
    private BigDecimal monthlyLimit;
    private Integer month;
    private Integer year;

    private Long userId;
}
