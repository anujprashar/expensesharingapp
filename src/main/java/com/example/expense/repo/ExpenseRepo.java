package com.example.expense.repo;

import com.example.expense.model.Expense;
import com.example.expense.model.GroupEntity;
import com.example.expense.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepo extends JpaRepository<Expense, Long> {
    List<Expense> findByGroup(GroupEntity group);
    List<Expense> findByCreatedBy(User user);
}
