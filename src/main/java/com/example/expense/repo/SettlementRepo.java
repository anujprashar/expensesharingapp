package com.example.expense.repo;

import com.example.expense.model.Expense;
import com.example.expense.model.GroupEntity;
import com.example.expense.model.Settlement;
import com.example.expense.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettlementRepo extends JpaRepository<Settlement, Long> {
    List<Settlement> findByPayerOrReceiver(User payer, User receiver);
    List<Settlement> findByPayer(User payer);
    List<Settlement> findByReceiver(User receiver);
	Settlement findByExpenseAndReceiverAndPayer(Expense expense, User receiver, User payer);
	List<Settlement> findByPayerAndGroup(User payer, GroupEntity group);
	List<Settlement> findByReceiverAndGroup(User receiver, GroupEntity group);

}
