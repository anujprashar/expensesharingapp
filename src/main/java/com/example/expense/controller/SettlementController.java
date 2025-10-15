package com.example.expense.controller;

import com.example.expense.dto.BalanceResponse;
import com.example.expense.dto.SettlementRequest;
import com.example.expense.model.Settlement;
import com.example.expense.service.SettlementService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settlements")
@RequiredArgsConstructor
public class SettlementController {
	@Autowired
    private final SettlementService settlementService;

    @PostMapping("/settle")
    public ResponseEntity<Settlement> settle(@RequestBody SettlementRequest req) {
        return ResponseEntity.ok(settlementService.settle(req.getPayerId(), req.getReceiverId(), req.getAmount(), req.getExpenseId()));
    }
    
    @PostMapping("/amountsToSettle")
    public ResponseEntity<List<Settlement>> amountsToSettle(@RequestBody SettlementRequest req) {
        return ResponseEntity.ok(settlementService.findByReceiver(req.getReceiverId()));
    }
    
    @PostMapping("/amountsToReceive")
    public ResponseEntity<List<Settlement>> amountsToReceive(@RequestBody SettlementRequest req) {
        return ResponseEntity.ok(settlementService.findByPayer(req.getPayerId()));
    }
    
    @GetMapping("/balance/{userId}/{groupId}")
    public ResponseEntity<BalanceResponse> amountsToReceive(@PathVariable Long userId, @PathVariable Long groupId) {
        return ResponseEntity.ok(settlementService.findBalance(userId, groupId));
    }
    
}
