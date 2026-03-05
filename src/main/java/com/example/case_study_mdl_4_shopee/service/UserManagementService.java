package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.dto.AccountForAdminDto;
import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.entity.TransactionHistory;
import com.example.case_study_mdl_4_shopee.enums.TransactionType;
import com.example.case_study_mdl_4_shopee.repository.IAccountRepository;
import com.example.case_study_mdl_4_shopee.repository.ITransactionHistoryRepository;
import com.example.case_study_mdl_4_shopee.service.impl.IUserManagementService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManagementService implements IUserManagementService {
    private final IAccountRepository accountRepository;
    private final ITransactionHistoryRepository transactionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AccountForAdminDto> listAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(account -> modelMapper.map(account, AccountForAdminDto.class))
                .toList();
    }

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Account findById(int id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUserAccount(int userId) {
        accountRepository.deleteById(userId);
    }

    @Override
    public void addAdminAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void lockUserAccount(int userId) {
        Account account = accountRepository.findById(userId).orElse(null);
        if (account != null) {
            account.setActive(false);
            accountRepository.save(account);
        }
    }

    @Override
    public List<Account> search(String username, String email, String phone) {

        if (username != null && username.trim().isEmpty()) {
            username = null;
        }

        if (email != null && email.trim().isEmpty()) {
            email = null;
        }

        if (phone != null && phone.trim().isEmpty()) {
            phone = null;
        }

        return accountRepository.searchMulti(username, email, phone);
    }

    @Override
    public void unlockUserAccount(int userId) {
        Account account = accountRepository.findById(userId).orElse(null);
        if (account != null) {
            account.setActive(true);
            accountRepository.save(account);
        }
    }

    @Override
    public void grantCertificatedSeller(int userId) {
        Account account = accountRepository.findById(userId).orElse(null);
        if (account != null) {
            account.setCertified(true);
            accountRepository.save(account);
        }
    }
    @Override
    public void removeCertificatedSeller(Integer id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null) {
            account.setCertified(false);
            accountRepository.save(account);
        }
    }

    @Override
    @Transactional
    public void deposit(Long userId, Long amount) {
        Account account = accountRepository.findById(userId).orElseThrow();
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        TransactionHistory transaction = TransactionHistory.builder()
                .accountTransaction(account)
                .amount(amount)
                .type(TransactionType.DEPOSIT)
                .description("Nạp tiền vào ví Shopee")
                .balanceAfter(account.getBalance())
                .build();
        transactionRepository.save(transaction);
    }
}
