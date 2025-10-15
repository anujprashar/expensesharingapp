package com.example.expense.repo;

import com.example.expense.model.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepo extends JpaRepository<GroupEntity, Long> {
}
