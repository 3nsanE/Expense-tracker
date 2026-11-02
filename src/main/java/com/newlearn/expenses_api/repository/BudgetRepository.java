package com.newlearn.expenses_api.repository;

import com.newlearn.expenses_api.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUserId(Long userId);

    Optional<Budget> findByIdAndMonthAndYear(
            Long userId,
            Integer month,
            Integer year
    );

    Optional<Budget> findByIdAndUserId(
            Long id,
            Long userId
    );

    boolean existsByUserIdAndMonthAndYear(
            Long userId,
            Integer month,
            Integer year
    );
}
