package com.example.case_study_mdl_4_shopee.repository;

import com.example.case_study_mdl_4_shopee.entity.TransactionHistory;
import com.example.case_study_mdl_4_shopee.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    List<TransactionHistory> findAllByAccountTransaction_AccountIdOrderByCreatedAtDesc(Long accountId);
    Page<TransactionHistory> findByAccountTransaction_AccountId(Long accountTransaction_accountId, Pageable pageable);

    Page<TransactionHistory> findByType(TransactionType type, Pageable pageable);
}
