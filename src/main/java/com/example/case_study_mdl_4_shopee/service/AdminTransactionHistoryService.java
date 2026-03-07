package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.entity.TransactionHistory;
import com.example.case_study_mdl_4_shopee.enums.TransactionType;
import com.example.case_study_mdl_4_shopee.repository.ITransactionHistoryRepository;
import com.example.case_study_mdl_4_shopee.service.impl.IAdminTransactionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AdminTransactionHistoryService implements IAdminTransactionHistoryService {

    private final ITransactionHistoryRepository transactionHistoryRepository;

    public Page<TransactionHistory> getAll(Pageable pageable) {
        return transactionHistoryRepository.findAll(pageable);
    }

    public Page<TransactionHistory> getByAccount(Long accountId, Pageable pageable) {
        return transactionHistoryRepository.findByAccountTransaction_AccountId(accountId, pageable);
    }

    public Page<TransactionHistory> getByType(TransactionType type, Pageable pageable) {
        return transactionHistoryRepository.findByType(type, pageable);
    }
}
