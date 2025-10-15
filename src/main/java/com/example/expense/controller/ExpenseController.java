package com.example.expense.controller;

import com.example.expense.dto.ExpenseRequest;
import com.example.expense.model.Expense;
import com.example.expense.service.ExpenseService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {
	
	@Autowired
    private final ExpenseService expenseService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<Expense> addExpense(@PathVariable Long userId, @RequestBody ExpenseRequest req) {
        return ResponseEntity.ok(expenseService.addExpense(userId, req));
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Expense>> byGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok(expenseService.getExpensesForGroup(groupId));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Expense>> byUser(@PathVariable Long userId) {
        return ResponseEntity.ok(expenseService.getExpensesForUser(userId));
    }
}
