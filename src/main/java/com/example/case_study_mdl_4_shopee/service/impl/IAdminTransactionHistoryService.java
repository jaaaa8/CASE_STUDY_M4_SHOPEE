package com.example.case_study_mdl_4_shopee.service.impl;

import com.example.case_study_mdl_4_shopee.entity.TransactionHistory;
import com.example.case_study_mdl_4_shopee.enums.TransactionType;
import com.example.case_study_mdl_4_shopee.repository.ITransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


public interface IAdminTransactionHistoryService {


    public Page<TransactionHistory> getAll(Pageable pageable);

    public Page<TransactionHistory> getByAccount(Long accountId, Pageable pageable);
    public Page<TransactionHistory> getByType(TransactionType type, Pageable pageable);
}
