package com.example.expense.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SettlementRequest {
    private Long payerId;
    private Long receiverId;
    private BigDecimal amount;
    private Long expenseId;
}
