package com.newlearn.expenses_api.service;

import com.newlearn.expenses_api.dto.request.BudgetRequest;
import com.newlearn.expenses_api.dto.response.BudgetResponse;

import java.util.List;

public interface BudgetService {
    BudgetResponse createBudget(Long userId, BudgetRequest request);
    List<BudgetResponse> getAllBudgets(Long userId);
    BudgetResponse getBudgetsById(Long budgetId,Long userId);
    BudgetResponse getBudgetsByMonthAndYear(Long userId, Integer month, Integer year);
    BudgetResponse updateBudget(Long budgetId, Long userId,BudgetRequest request);

    void deleteBudget(Long budgetId, Long userId);
}
