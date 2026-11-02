package com.newlearn.expenses_api.serviceImpl;

import com.newlearn.expenses_api.dto.request.BudgetRequest;
import com.newlearn.expenses_api.dto.response.BudgetResponse;
import com.newlearn.expenses_api.entity.Budget;
import com.newlearn.expenses_api.entity.User;
import com.newlearn.expenses_api.exception.DuplicateResourceException;
import com.newlearn.expenses_api.exception.ResourceNotFoundException;
import com.newlearn.expenses_api.mapper.BudgetMapper;
import com.newlearn.expenses_api.repository.BudgetRepository;
import com.newlearn.expenses_api.repository.UserRepository;
import com.newlearn.expenses_api.service.BudgetService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final BudgetMapper budgetMapper;

    // Create Budget
    @Override
    public BudgetResponse createBudget(Long userId, BudgetRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with ID: " + userId
                ));

        if (budgetRepository.existsByUserIdAndMonthAndYear(
                userId, request.getMonth(), request.getYear())) {

            throw new DuplicateResourceException(
                    "Budget already exists for "
                            + request.getMonth() + "/" + request.getYear()
            );
        }

        Budget budget = budgetMapper.toEntity(request, user);

        Budget savedBudget = budgetRepository.save(budget);

        return budgetMapper.toResponse(savedBudget);
    }

    // Get All Budgets
    @Override
    @Transactional(readOnly = true)
    public List<BudgetResponse> getAllBudgets(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with ID: " + userId
            );
        }

        return budgetRepository.findByUserId(userId)
                .stream()
                .map(budgetMapper::toResponse)
                .toList();
    }

    // Get Budget By ID
    @Override
    @Transactional(readOnly = true)
    public BudgetResponse getBudgetsById(Long budgetId, Long userId) {

        Budget budget = budgetRepository
                .findByIdAndUserId(budgetId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Budget not found with ID: " + budgetId
                ));

        return budgetMapper.toResponse(budget);
    }

    // Get Budget By Month And Year
    @Override
    @Transactional(readOnly = true)
    public BudgetResponse getBudgetsByMonthAndYear(
            Long userId, Integer month, Integer year) {

        Budget budget = budgetRepository
                .findByIdAndMonthAndYear(userId, month, year)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Budget not found for " + month + "/" + year
                ));

        return budgetMapper.toResponse(budget);
    }

    // Update Budget
    @Override
    public BudgetResponse updateBudget(
            Long budgetId, Long userId, BudgetRequest request) {

        Budget budget = budgetRepository
                .findByIdAndUserId(budgetId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Budget not found with ID: " + budgetId
                ));

        boolean duplicateBudget =
                !budget.getMonth().equals(request.getMonth())
                        || !budget.getYear().equals(request.getYear());

        if (duplicateBudget && budgetRepository.existsByUserIdAndMonthAndYear(
                userId, request.getMonth(), request.getYear())) {

            throw new DuplicateResourceException(
                    "Budget already exists for "
                            + request.getMonth() + "/" + request.getYear()
            );
        }

        budgetMapper.updateEntity(request,budget);

        Budget updatedBudget = budgetRepository.save(budget);

        return budgetMapper.toResponse(updatedBudget);
    }

    // Delete Budget
    @Override
    public void deleteBudget(Long budgetId, Long userId) {

        Budget budget = budgetRepository
                .findByIdAndUserId(budgetId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Budget not found with ID: " + budgetId
                ));

        budgetRepository.delete(budget);
    }
}