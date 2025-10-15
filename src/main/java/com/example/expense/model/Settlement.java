package com.example.expense.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "settlements")
public class Settlement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User payer;

    @ManyToOne(optional = false)
    private User receiver;

    private BigDecimal amount;

    @ManyToOne
    private Expense expense;

    @ManyToOne
    private GroupEntity group;
    
    private LocalDateTime createdAt;
}
