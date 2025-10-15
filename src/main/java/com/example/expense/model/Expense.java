package com.example.expense.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "expenses")
public class Expense {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private BigDecimal amount;

    @ManyToOne(optional = false)
    private User createdBy;

    @ManyToOne(optional = false)
    private GroupEntity group;

    private LocalDateTime createdAt;

    @Version
    private Long version;
}
