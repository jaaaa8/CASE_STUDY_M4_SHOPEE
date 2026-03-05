package com.example.case_study_mdl_4_shopee.config;

import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.entity.AccountRole;
import com.example.case_study_mdl_4_shopee.entity.Role;
import com.example.case_study_mdl_4_shopee.repository.IAccountRepository;
import com.example.case_study_mdl_4_shopee.repository.IRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(IAccountRepository accountRepository,
                               IRoleRepository roleRepository,
                               PasswordEncoder passwordEncoder) {

        return args -> {

            // tạo role nếu chưa có
            Role adminRole = roleRepository.findByRoleName("ADMIN");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setRoleName("ADMIN");
                roleRepository.save(adminRole);
            }

            Role userRole = roleRepository.findByRoleName("USER");
            if (userRole == null) {
                userRole = new Role();
                userRole.setRoleName("USER");
                roleRepository.save(userRole);
            }

            // tạo admin
            if (accountRepository.findByUsername("admin").isEmpty()) {

                Account admin = new Account();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setEmail("admin@gmail.com");
                admin.setPhone("0900000001");
                admin.setAddress("Da Nang");

                AccountRole accountRole = new AccountRole();
                accountRole.setAccount(admin);
                accountRole.setRole(adminRole);

                admin.getAccountRoles().add(accountRole);

                accountRepository.save(admin);

                System.out.println("Admin created: admin / 123456");
            }

            // tạo user test
            if (accountRepository.findByUsername("user").isEmpty()) {

                Account user = new Account();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("123456"));
                user.setEmail("user@gmail.com");
                user.setPhone("0900000002");
                user.setAddress("Da Nang");

                AccountRole accountRole = new AccountRole();
                accountRole.setAccount(user);
                accountRole.setRole(userRole);

                user.getAccountRoles().add(accountRole);

                accountRepository.save(user);

                System.out.println("User created: user / 123456");
            }

        };
    }
}