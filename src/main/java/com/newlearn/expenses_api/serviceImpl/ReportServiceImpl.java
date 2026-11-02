package com.newlearn.expenses_api.serviceImpl;

import com.newlearn.expenses_api.dto.response.CategorySummaryResponse;
import com.newlearn.expenses_api.dto.response.ExpenseSummaryResponse;
import com.newlearn.expenses_api.entity.Expense;
import com.newlearn.expenses_api.exception.ResourceNotFoundException;
import com.newlearn.expenses_api.repository.ExpenseRepository;
import com.newlearn.expenses_api.repository.UserRepository;
import com.newlearn.expenses_api.service.ReportService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    // Get Expense Summary
    @Override
    public ExpenseSummaryResponse getExpenseSummary(
            Long userId, LocalDate startDate, LocalDate endDate) {

        validateReportRequest(userId, startDate, endDate);

        List<Expense> expenses =
                expenseRepository.findByUserIdAndExpenseDateBetween(
                        userId, startDate, endDate
                );

        if (expenses.isEmpty()) {
            return ExpenseSummaryResponse.builder()
                    .totalExpenses(BigDecimal.ZERO)
                    .totalTransactions(0L)
                    .averageExpenses(BigDecimal.ZERO)
                    .highestExpenses(BigDecimal.ZERO)
                    .lowestExpenses(BigDecimal.ZERO)
                    .build();
        }

        BigDecimal total = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal highest = expenses.stream()
                .map(Expense::getAmount)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal lowest = expenses.stream()
                .map(Expense::getAmount)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal average = total.divide(
                BigDecimal.valueOf(expenses.size()),
                2,
                RoundingMode.HALF_UP
        );

        return ExpenseSummaryResponse.builder()
                .totalExpenses(total)
                .totalTransactions((long) expenses.size())
                .averageExpenses(average)
                .highestExpenses(highest)
                .lowestExpenses(lowest)
                .build();
    }

    // Get Category-wise Expense Summary
    @Override
    public List<CategorySummaryResponse> getCategorySummary(
            Long userId, LocalDate startDate, LocalDate endDate) {

        validateReportRequest(userId, startDate, endDate);

        List<Expense> expenses =
                expenseRepository.findByUserIdAndExpenseDateBetween(
                        userId, startDate, endDate
                );

        Map<Long, List<Expense>> groupedExpenses = expenses.stream()
                .collect(Collectors.groupingBy(
                        expense -> expense.getCategory().getId()
                ));

        return groupedExpenses.entrySet().stream()
                .map(entry -> {

                    List<Expense> categoryExpenses = entry.getValue();

                    BigDecimal totalAmount = categoryExpenses.stream()
                            .map(Expense::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    String categoryName = categoryExpenses.get(0)
                            .getCategory().getName();

                    return CategorySummaryResponse.builder()
                            .categoryId(entry.getKey())
                            .categoryName(categoryName)
                            .totalAmount(totalAmount)
                            .totalExpenses((long) categoryExpenses.size())
                            .build();
                })
                .toList();
    }

    // Validate Report Request
    private void validateReportRequest(
            Long userId, LocalDate startDate, LocalDate endDate) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with ID: " + userId
            );
        }

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException(
                    "Start date and end date are required"
            );
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                    "Start date cannot be after end date"
            );
        }
    }
}