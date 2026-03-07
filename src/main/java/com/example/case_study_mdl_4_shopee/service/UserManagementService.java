package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.dto.AccountForAdminDto;
import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.entity.AccountRole;
import com.example.case_study_mdl_4_shopee.entity.TransactionHistory;
import com.example.case_study_mdl_4_shopee.enums.TransactionType;
import com.example.case_study_mdl_4_shopee.repository.IAccountRepository;
import com.example.case_study_mdl_4_shopee.repository.IAccountRoleRepository;
import com.example.case_study_mdl_4_shopee.repository.ITransactionHistoryRepository;
import com.example.case_study_mdl_4_shopee.service.impl.IUserManagementService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManagementService implements IUserManagementService {
    private final IAccountRepository accountRepository;
    private final IAccountRoleRepository accountRoleRepository;
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
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUserAccount(Long userId) {
        accountRepository.deleteById(userId);
    }

    @Override
    public void addAdminAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void removeCertificatedSeller(Long id) {

    }

    @Override
    @Transactional
    public void lockUserAccount(Long userId) {
        // Tìm tất cả các role của user này
        List<AccountRole> roles = accountRoleRepository.findAllByAccount_AccountId(userId);
        if (!roles.isEmpty()) {
            roles.forEach(role -> role.setActive(false));
            accountRoleRepository.saveAll(roles);
        }
    }

    @Override
    @Transactional
    public void unlockUserAccount(Long userId) {
        List<AccountRole> roles = accountRoleRepository.findAllByAccount_AccountId(userId);
        if (!roles.isEmpty()) {
            roles.forEach(role -> role.setActive(true));
            accountRoleRepository.saveAll(roles);
        }
    }

    public List<AccountForAdminDto> search(String username, String email, String phone) {

        List<Account> accounts = accountRepository.searchMulti(username,email,phone);

        return accounts.stream()
                .map(AccountForAdminDto::new)
                .toList();
    }


    @Override
    public void grantCertificatedSeller(Long userId) {
        Account account = accountRepository.findById(userId).orElse(null);
        if (account != null) {
            account.setCertified(true);
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

    @Override
    public Account getCurrentAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        String username;
        if (authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else {
            username = authentication.getName();
        }

        return accountRepository.findByUsername(username).orElse(null);
    }
}
