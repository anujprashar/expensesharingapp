package com.example.expense.service;

import com.example.expense.dto.ExpenseRequest;
import com.example.expense.model.*;
import com.example.expense.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepo expenseRepo;
    private final GroupRepo groupRepo;
    private final UserRepo userRepo;
    private final MembershipRepo membershipRepo;
    private final SettlementRepo settlementRepo;

    @Transactional
    public Expense addExpense(Long userId, ExpenseRequest req) {
        User creator = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        GroupEntity group = groupRepo.findById(req.getGroupId()).orElseThrow(() -> new RuntimeException("Group not found"));

        // ensure user is member
        membershipRepo.findByUserAndGroup(creator, group)
                .orElseThrow(() -> new RuntimeException("User not member of group"));

        Expense e = Expense.builder()
                .title(req.getTitle())
                .amount(req.getAmount())
                .createdBy(creator)
                .group(group)
                .createdAt(LocalDateTime.now())
                .build();

        Expense saved = expenseRepo.save(e);

        // Create implied settlements by computing splits (not persisted as Settlement for every split here
        // For demo we persist settlement entries of "owing" from each participant to creator (who paid)
        List<Membership> members = membershipRepo.findByGroup(group);
        List<User> users = new ArrayList<>();
        for (Membership m : members) users.add(m.getUser());

        Map<Long, BigDecimal> splits = req.getSplits();
        if (splits == null || splits.isEmpty()) {
        	BigDecimal usersSize = new BigDecimal(users.size());
            // equal split
            BigDecimal per = req.getAmount().divide(usersSize,2,RoundingMode.HALF_UP);
            for (User u : users) {
                if (u.getId().equals(creator.getId())) continue;
                Settlement s = Settlement.builder()
                        .payer(u)
                        .receiver(creator)
                        .amount(per)
                        .expense(saved)
                        .group(group)
                        .createdAt(LocalDateTime.now())
                        .build();
                settlementRepo.save(s);
            }
        } else {
            // custom splits provided as userId->amount they owe
            for (Map.Entry<Long, BigDecimal> en : splits.entrySet()) {
                if (en.getKey().equals(creator.getId())) continue;
                User u = userRepo.findById(en.getKey()).orElseThrow(() -> new RuntimeException("User in split not found"));
                Settlement s = Settlement.builder()
                        .payer(u)
                        .receiver(creator)
                        .amount(en.getValue())
                        .expense(saved)
                        .group(group)
                        .createdAt(LocalDateTime.now())
                        .build();
                settlementRepo.save(s);
            }
        }

        return saved;
    }

    public List<Expense> getExpensesForGroup(Long groupId) {
        GroupEntity g = groupRepo.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        return expenseRepo.findByGroup(g);
    }
    
    public List<Expense> getExpensesForUser(Long userId) {
        User u = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return expenseRepo.findByCreatedBy(u);
    }
}
