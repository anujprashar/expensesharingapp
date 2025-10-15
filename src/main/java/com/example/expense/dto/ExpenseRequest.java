package com.example.expense.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ExpenseRequest {
    private String title;
    private BigDecimal amount;
    private Long groupId;
    // Map of userId -> share amount (optional). If null, equal split used.
    private Map<Long, BigDecimal> splits;
}
