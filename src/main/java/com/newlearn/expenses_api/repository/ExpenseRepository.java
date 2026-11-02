package com.newlearn.expenses_api.repository;

import com.newlearn.expenses_api.entity.Category;
import com.newlearn.expenses_api.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId);

    Optional<Expense> findByIdAndUserId(Long id, Long userId);

    List<Expense> findByUserIdAndExpenseDateBetween(
            Long userId,
            LocalDate startDate,
            LocalDate endDate
    );

    List<Expense> findByUserIdAndCategory(Long userId, Long categoryId);

    List<Expense> findByExpenseDateBetweenAndCategory(
            Long userId,
            LocalDate startDate,
            LocalDate endDate,
            Category category);

}
