package com.newlearn.expenses_api.service;

import com.newlearn.expenses_api.dto.request.ExpensesRequest;
import com.newlearn.expenses_api.dto.response.ExpenseResponse;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    ExpenseResponse createExpense(Long userId, ExpensesRequest request);
    List<ExpenseResponse> getAllExpenses(Long userId);
    ExpenseResponse getExpenseById(Long expenseId,Long userId);
    ExpenseResponse updateExpense(Long expenseId, Long userId,ExpensesRequest request);

    void deleteExpense(Long expenseId, Long userId);

    List<ExpenseResponse> getExpensesByDateRange(Long userId, LocalDate startDate, LocalDate endDate);
    List<ExpenseResponse> getExpensesByCategory(Long userId, Long categoryId);
}
