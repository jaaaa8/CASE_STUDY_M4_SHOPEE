package com.example.case_study_mdl_4_shopee.entity;

import com.example.case_study_mdl_4_shopee.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactionHistory",
        indexes = {
                @Index(name = "idx_transaction_account", columnList = "accountId"),
                @Index(name = "idx_transaction_type", columnList = "type")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactionId")
    private Integer transactionId;

    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    private Account accountTransaction;

    @Column(nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    private String description;
    private Long balanceAfter;

    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;
}
