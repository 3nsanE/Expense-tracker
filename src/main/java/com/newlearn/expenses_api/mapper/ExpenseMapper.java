package com.newlearn.expenses_api.mapper;

import com.newlearn.expenses_api.dto.request.ExpensesRequest;
import com.newlearn.expenses_api.dto.response.ExpenseResponse;
import com.newlearn.expenses_api.entity.Category;
import com.newlearn.expenses_api.entity.Expense;
import com.newlearn.expenses_api.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {
    public Expense toEntity(ExpensesRequest request, User user, Category category) {
        return Expense.builder().
                title(request.getTitle()).
                description(request.getDescription()).
                amount(request.getAmount()).
                user(user).
                expenseDate(request.getExpensesDate()).
                category(category).
                build();
    }

    public ExpenseResponse toResponse(Expense expense) {
        return ExpenseResponse.builder().
                id(expense.getId()).
                title(expense.getTitle()).
                description(expense.getDescription()).
                amount(expense.getAmount()).
                expenseDate(expense.getExpenseDate()).
                categoryId(expense.getCategory().getId()).
                categoryName(expense.getCategory().getName()).
                userId(expense.getUser().getId()).
                createdAt(expense.getCreatedDate()).
                build();
    }

    public void updateEntity(ExpensesRequest request, Expense expense,Category category) {
        expense.setTitle(request.getTitle());
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setExpenseDate(request.getExpensesDate());
        expense.setCategory(category);
    }
}
