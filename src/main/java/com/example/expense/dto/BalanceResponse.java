package com.example.expense.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BalanceResponse {
    private BigDecimal amountToPay;
    private BigDecimal amountToReceive;

}
