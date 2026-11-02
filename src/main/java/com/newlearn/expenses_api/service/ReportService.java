package com.newlearn.expenses_api.service;

import com.newlearn.expenses_api.dto.response.CategorySummaryResponse;
import com.newlearn.expenses_api.dto.response.ExpenseSummaryResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    ExpenseSummaryResponse getExpenseSummary(Long userId, LocalDate startDate, LocalDate endDate);
    List<CategorySummaryResponse> getCategorySummary(Long userId, LocalDate startDate, LocalDate endDate);
}
