package com.newlearn.expenses_api.controller;

import com.newlearn.expenses_api.dto.request.ExpensesRequest;
import com.newlearn.expenses_api.dto.response.ExpenseResponse;
import com.newlearn.expenses_api.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(
            @PathVariable Long userId,
            @Valid @RequestBody ExpensesRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(expenseService.createExpense(userId, request));
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses(
            @PathVariable Long userId) {

        return ResponseEntity.ok(expenseService.getAllExpenses(userId));
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponse> getExpenseById(
            @PathVariable Long userId,
            @PathVariable Long expenseId) {

        return ResponseEntity.ok(
                expenseService.getExpenseById(expenseId, userId));
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponse> updateExpense(
            @PathVariable Long userId,
            @PathVariable Long expenseId,
            @Valid @RequestBody ExpensesRequest request) {

        return ResponseEntity.ok(
                expenseService.updateExpense(expenseId, userId, request));
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(
            @PathVariable Long userId,
            @PathVariable Long expenseId) {

        expenseService.deleteExpense(expenseId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate) {

        return ResponseEntity.ok(
                expenseService.getExpensesByDateRange(userId, startDate, endDate));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByCategory(
            @PathVariable Long userId,
            @PathVariable Long categoryId) {

        return ResponseEntity.ok(
                expenseService.getExpensesByCategory(userId, categoryId));
    }
}