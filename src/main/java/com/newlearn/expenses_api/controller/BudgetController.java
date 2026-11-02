package com.newlearn.expenses_api.controller;

import com.newlearn.expenses_api.dto.request.BudgetRequest;
import com.newlearn.expenses_api.dto.response.BudgetResponse;
import com.newlearn.expenses_api.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public ResponseEntity<BudgetResponse> createBudget(
            @PathVariable Long userId,
            @Valid @RequestBody BudgetRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(budgetService.createBudget(userId, request));
    }

    @GetMapping
    public ResponseEntity<List<BudgetResponse>> getAllBudgets(
            @PathVariable Long userId) {

        return ResponseEntity.ok(budgetService.getAllBudgets(userId));
    }

    @GetMapping("/{budgetId}")
    public ResponseEntity<BudgetResponse> getBudgetById(
            @PathVariable Long userId,
            @PathVariable Long budgetId) {

        return ResponseEntity.ok(
                budgetService.getBudgetsById(budgetId, userId));
    }

    @GetMapping("/monthly")
    public ResponseEntity<BudgetResponse> getBudgetByMonthAndYear(
            @PathVariable Long userId,
            @RequestParam Integer month,
            @RequestParam Integer year) {

        return ResponseEntity.ok(
                budgetService.getBudgetsByMonthAndYear(userId, month, year));
    }

    @PutMapping("/{budgetId}")
    public ResponseEntity<BudgetResponse> updateBudget(
            @PathVariable Long userId,
            @PathVariable Long budgetId,
            @Valid @RequestBody BudgetRequest request) {

        return ResponseEntity.ok(
                budgetService.updateBudget(budgetId, userId, request));
    }

    @DeleteMapping("/{budgetId}")
    public ResponseEntity<Void> deleteBudget(
            @PathVariable Long userId,
            @PathVariable Long budgetId) {

        budgetService.deleteBudget(budgetId, userId);
        return ResponseEntity.noContent().build();
    }
}