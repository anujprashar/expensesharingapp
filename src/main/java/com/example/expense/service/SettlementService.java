package com.example.expense.service;

import com.example.expense.dto.BalanceResponse;
import com.example.expense.model.Expense;
import com.example.expense.model.GroupEntity;
import com.example.expense.model.Settlement;
import com.example.expense.model.User;
import com.example.expense.repo.ExpenseRepo;
import com.example.expense.repo.GroupRepo;
import com.example.expense.repo.SettlementRepo;
import com.example.expense.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SettlementService {
    private final SettlementRepo settlementRepo;
    private final UserRepo userRepo;
    private final ExpenseRepo expenseRepo;
    private final GroupRepo groupRepo;

    // transactional settlement: create a settlement record representing money moved.
    @Transactional
    public Settlement settle(Long payerId, Long receiverId, BigDecimal amount, Long expenseId) {
        User payer = userRepo.findById(payerId).orElseThrow(() -> new RuntimeException("Payer not found"));
        User receiver = userRepo.findById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));
        Expense expense = expenseRepo.findById(expenseId).orElseThrow(() -> new RuntimeException("Expense not found"));

        Settlement settlement = settlementRepo.findByExpenseAndReceiverAndPayer(expense,receiver,payer);
        BigDecimal settlementAmount = settlement.getAmount();
        if(settlementAmount.compareTo(amount)>=0) {
        	settlement.setAmount(settlementAmount.subtract(amount));
        }
        else {
        	throw new RuntimeException("Amount is incorrect");
        }
        return settlementRepo.save(settlement);
    }
    
    public List<Settlement> findByPayer(Long payerId) {
        User payer = userRepo.findById(payerId).orElseThrow(() -> new RuntimeException("Payer not found"));
        return settlementRepo.findByPayer(payer);
        
    }
    
    public List<Settlement> findByReceiver(Long receiverId) {
        User receiver = userRepo.findById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));
        return settlementRepo.findByReceiver(receiver);
        
    }

	public BalanceResponse findBalance(Long userId, Long groupId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        GroupEntity group = groupRepo.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));

        List<Settlement> payList =settlementRepo.findByPayerAndGroup(user, group);
        BigDecimal totalPay = payList.stream()
                .map(Settlement::getAmount) // Map objects to their BigDecimal price
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Reduce to sum
        
        List<Settlement> receiveList = settlementRepo.findByReceiverAndGroup(user,group);
        BigDecimal totalReceive = receiveList.stream()
                .map(Settlement::getAmount) // Map objects to their BigDecimal price
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Reduce to sum
        
        BalanceResponse balanceResponse = new BalanceResponse();
        balanceResponse.setAmountToPay(totalPay);
        balanceResponse.setAmountToReceive(totalReceive);
		return balanceResponse;
	}
	
}
