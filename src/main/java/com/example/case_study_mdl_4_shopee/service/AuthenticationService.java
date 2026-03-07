package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.entity.AccountRole;
import com.example.case_study_mdl_4_shopee.entity.Role;
import com.example.case_study_mdl_4_shopee.repository.IAccountRepository;
import com.example.case_study_mdl_4_shopee.repository.IRoleRepository;
import com.example.case_study_mdl_4_shopee.service.impl.IAuthenticationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService implements IAuthenticationService {
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    @Override
    public boolean register(String username, String password, String email, String phone, String address) {
        try {

            if (accountRepository.findByUsername(username).isPresent()) {
                return false;
            }

            if (accountRepository.existsByPhone(phone)) {
                return false;
            }

            if (accountRepository.existsByEmail(email)) {
                return false;
            }

            Role roleUser = roleRepository.findByRoleName("ROLE_CUSTOMER");

            Account account = new Account();
            account.setUsername(username);
            account.setPassword(passwordEncoder.encode(password));
            account.setEmail(email);
            account.setPhone(phone);
            account.setAddress(address);

            AccountRole accountRole = new AccountRole();
            accountRole.setAccount(account);
            accountRole.setRole(roleUser);

            account.getAccountRoles().add(accountRole);

            accountRepository.save(account);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public String refreshToken(String token) {
        try {
            String username = Jwts.parser()
                    .verifyWith((SecretKey) getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();

            return generateToken(username);

        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Account getCurrentAccount() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String username = authentication.getName();

        return accountRepository
                .findByUsername(username)
                .orElse(null);
    }
}
