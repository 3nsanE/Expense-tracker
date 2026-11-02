package com.newlearn.expenses_api.serviceImpl;

import com.newlearn.expenses_api.dto.request.ExpensesRequest;
import com.newlearn.expenses_api.dto.response.ExpenseResponse;
import com.newlearn.expenses_api.entity.Category;
import com.newlearn.expenses_api.entity.Expense;
import com.newlearn.expenses_api.entity.User;
import com.newlearn.expenses_api.exception.ResourceNotFoundException;
import com.newlearn.expenses_api.mapper.ExpenseMapper;
import com.newlearn.expenses_api.repository.CategoryRepository;
import com.newlearn.expenses_api.repository.ExpenseRepository;
import com.newlearn.expenses_api.repository.UserRepository;
import com.newlearn.expenses_api.service.ExpenseService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseMapper expenseMapper;

    // Create Expense
    @Override
    public ExpenseResponse createExpense(Long userId, ExpensesRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with ID: " + userId
                ));

        Category category = categoryRepository
                .findByIdAndUserId(request.getCategoryId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with ID: " + request.getCategoryId()
                ));

        Expense expense = expenseMapper.toEntity(request, user, category);

        Expense savedExpense = expenseRepository.save(expense);

        return expenseMapper.toResponse(savedExpense);
    }

    // Get All Expenses
    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponse> getAllExpenses(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with ID: " + userId
            );
        }

        return expenseRepository.findByUserId(userId)
                .stream()
                .map(expenseMapper::toResponse)
                .toList();
    }

    // Get Expense By ID
    @Override
    @Transactional(readOnly = true)
    public ExpenseResponse getExpenseById(Long expenseId, Long userId) {

        Expense expense = expenseRepository
                .findByIdAndUserId(expenseId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Expense not found with ID: " + expenseId
                ));

        return expenseMapper.toResponse(expense);
    }

    // Update Expense
    @Override
    public ExpenseResponse updateExpense(
            Long expenseId, Long userId, ExpensesRequest request) {

        Expense expense = expenseRepository
                .findByIdAndUserId(expenseId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Expense not found with ID: " + expenseId
                ));

        Category category = categoryRepository
                .findByIdAndUserId(request.getCategoryId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with ID: " + request.getCategoryId()
                ));

        expenseMapper.updateEntity(request,expense,  category);

        Expense updatedExpense = expenseRepository.save(expense);

        return expenseMapper.toResponse(updatedExpense);
    }

    // Delete Expense
    @Override
    public void deleteExpense(Long expenseId, Long userId) {

        Expense expense = expenseRepository
                .findByIdAndUserId(expenseId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Expense not found with ID: " + expenseId
                ));

        expenseRepository.delete(expense);
    }

    // Get Expenses By Date Range
    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponse> getExpensesByDateRange(
            Long userId, LocalDate startDate, LocalDate endDate) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with ID: " + userId
            );
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                    "Start date cannot be after end date"
            );
        }

        return expenseRepository
                .findByUserIdAndExpenseDateBetween(userId, startDate, endDate)
                .stream()
                .map(expenseMapper::toResponse)
                .toList();
    }

    // Get Expenses By Category
    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponse> getExpensesByCategory(
            Long userId, Long categoryId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with ID: " + userId
            );
        }

        if (!categoryRepository.existsByIdAndUserId(categoryId, userId)) {
            throw new ResourceNotFoundException(
                    "Category not found with ID: " + categoryId
            );
        }

        return expenseRepository.findByUserIdAndCategory(userId, categoryId)
                .stream()
                .map(expenseMapper::toResponse)
                .toList();
    }
}