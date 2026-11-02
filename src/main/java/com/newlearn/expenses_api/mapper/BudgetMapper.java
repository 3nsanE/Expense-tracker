package com.newlearn.expenses_api.mapper;

import com.newlearn.expenses_api.dto.request.BudgetRequest;
import com.newlearn.expenses_api.dto.response.BudgetResponse;
import com.newlearn.expenses_api.entity.Budget;
import com.newlearn.expenses_api.entity.User;
import org.springframework.stereotype.Component;

@Component
public class BudgetMapper {
    public Budget toEntity(BudgetRequest request, User user) {
        return Budget.builder().
                monthlyLimit(request.getMonthlyLimit()).
                month(request.getMonth()).
                year(request.getYear()).
                build();
    }

    public BudgetResponse toResponse(Budget budget) {
        return BudgetResponse.builder().
                id(budget.getId()).
                monthlyLimit(budget.getMonthlyLimit()).
                month(budget.getMonth()).
                year(budget.getYear()).
                userId(budget.getUser().getId()).
                build();
    }

    public void  updateEntity(BudgetRequest request, Budget budget) {
        budget.setMonthlyLimit(request.getMonthlyLimit());
        budget.setMonth(request.getMonth());
        budget.setYear(request.getYear());
    }
}
