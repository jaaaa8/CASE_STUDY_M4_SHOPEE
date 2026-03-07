package com.example.case_study_mdl_4_shopee.controller.admin;

import com.example.case_study_mdl_4_shopee.entity.TransactionHistory;

import com.example.case_study_mdl_4_shopee.service.impl.IAdminTransactionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/transactions")
@RequiredArgsConstructor
public class TransactionHistoryController {

    private final IAdminTransactionHistoryService transactionHistoryService;

    @GetMapping
    public String listTransactions(
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {

        Page<TransactionHistory> transactions =
                transactionHistoryService.getAll(PageRequest.of(page, 10));

        return "admin/transaction/list";
    }
}