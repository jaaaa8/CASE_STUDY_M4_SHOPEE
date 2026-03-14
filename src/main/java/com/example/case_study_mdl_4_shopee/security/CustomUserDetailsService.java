package com.example.case_study_mdl_4_shopee.security;

import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.entity.AccountRole;
import com.example.case_study_mdl_4_shopee.repository.IAccountRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // 1. Lấy danh sách các quyền đang ACTIVE
        Set<GrantedAuthority> authorities =
                account.getAccountRoles().stream()
                        .filter(AccountRole::isActive) // Chỉ lấy những role có active = true
                        .map(ar -> new SimpleGrantedAuthority(ar.getRole().getRoleName()))
                        .collect(Collectors.toSet());

        // 2. Kiểm tra xem tài khoản có quyền nào đang hoạt động không
        // Nếu authorities trống rỗng, nghĩa là tất cả các role của account này đã bị lock (active = false)
        boolean isAccountEnabled = !authorities.isEmpty();

        return new User(
                account.getUsername(),
                account.getPassword(),
                isAccountEnabled,       // enabled: Nếu không có role nào active thì chặn đăng nhập
                true,                   // accountNonExpired
                true,                   // credentialsNonExpired
                true,                   // accountNonLocked
                authorities
        );
    }
}
